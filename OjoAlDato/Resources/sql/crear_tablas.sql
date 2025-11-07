-- =============================================
-- Script de creación de la base de datos OjoAlDato
-- =============================================
DROP DATABASE IF EXISTS ojoaldato;

-- Crear la base de datos (si no existe)
CREATE DATABASE IF NOT EXISTS ojoaldato 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_0900_ai_ci;

USE ojoaldato;

-- =============================================
-- Tabla: clientes
-- Almacena información de los clientes (estándar y premium)
-- =============================================
CREATE TABLE clientes (
    email VARCHAR(100) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    domicilio VARCHAR(200) NOT NULL,
    nif VARCHAR(20) NOT NULL UNIQUE,
    tipo ENUM('ESTANDAR', 'PREMIUM') NOT NULL,
    fecha_alta DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    CONSTRAINT chk_email CHECK (email LIKE '%@%.%')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================================
-- Tabla: articulos
-- Almacena la información de los artículos disponibles
-- =============================================
CREATE TABLE articulos (
    codigo VARCHAR(20) PRIMARY KEY,
    descripcion VARCHAR(200) NOT NULL,
    pvp DECIMAL(10,2) NOT NULL,
    gastos_envio DECIMAL(10,2) NOT NULL,
    tiempo_preparacion INT NOT NULL, -- en minutos
    stock INT DEFAULT 0,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_pvp_positivo CHECK (pvp >= 0),
    CONSTRAINT chk_gastos_envio_positivos CHECK (gastos_envio >= 0),
    CONSTRAINT chk_stock_no_negativo CHECK (stock >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================================
-- Tabla: pedidos
-- Registra los pedidos realizados por los clientes
-- =============================================
CREATE TABLE pedidos (
    num_pedido INT AUTO_INCREMENT PRIMARY KEY,
    email_cliente VARCHAR(100) NOT NULL,
    codigo_articulo VARCHAR(20) NOT NULL,
    cantidad INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    enviado BOOLEAN DEFAULT FALSE,
    fecha_envio DATETIME NULL,
    precio_total DECIMAL(10,2) NOT NULL,
    gastos_envio DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (email_cliente) REFERENCES clientes(email) ON DELETE RESTRICT,
    FOREIGN KEY (codigo_articulo) REFERENCES articulos(codigo) ON DELETE RESTRICT,
    CONSTRAINT chk_cantidad_positiva CHECK (cantidad > 0),
    INDEX idx_fecha_hora (fecha_hora),
    INDEX idx_cliente (email_cliente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- =============================================
-- Función: obtener_factor_descuento_envio
-- Devuelve el factor de descuento del envío (ej. 0.80 para 20%)
-- =============================================
CREATE FUNCTION obtener_factor_descuento_envio(p_email_cliente VARCHAR(100))
RETURNS DECIMAL(5,2)
READS SQL DATA
BEGIN
    DECLARE v_descuento DECIMAL(5,2);

    SELECT
        CASE
            WHEN tipo = 'PREMIUM' THEN 1 - (descuento_envio / 100)
            ELSE 1.00
        END
    INTO v_descuento
    FROM clientes
    WHERE email = p_email_cliente;

    IF v_descuento IS NULL THEN
        RETURN 1.00;
    END IF;

    RETURN v_descuento;
END;

-- =============================================
-- Procedimiento: crear_pedido
-- Crea un nuevo pedido con validaciones
-- =============================================
DELIMITER //
CREATE PROCEDURE crear_pedido(
    IN p_email_cliente VARCHAR(100),
    IN p_codigo_articulo VARCHAR(20),
    IN p_cantidad INT
)
BEGIN
    DECLARE v_precio_unitario DECIMAL(10,2);
    DECLARE v_gastos_envio_base DECIMAL(10,2);
    DECLARE v_factor_descuento DECIMAL(5,2);
    DECLARE v_gastos_final DECIMAL(5,2);
    
    -- Verificar si el cliente existe y está activo
    IF NOT EXISTS (SELECT 1 FROM clientes WHERE email = p_email_cliente AND activo = TRUE) THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Cliente no encontrado o inactivo';
    END IF;
    
    -- Verificar si el artículo existe, está activo y hay stock suficiente
    SELECT pvp, gastos_envio, stock >= p_cantidad
    INTO v_precio_unitario, v_gastos_envio_base, @stock_suficiente
    FROM articulos 
    WHERE codigo = p_codigo_articulo AND activo = TRUE;
    
    IF v_precio_unitario IS NULL THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Artículo no encontrado o inactivo';
    END IF;
    
    IF @stock_suficiente = 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Stock insuficiente';
    END IF;
    
    SET v_factor_descuento = obtener_factor_descuento_envio(p_email_cliente);
    SET v_gastos_final = (v_gastos_envio_base * p_cantidad) * v_factor_descuento;

    -- Insertar el pedido
    INSERT INTO pedidos (
        email_cliente, 
        codigo_articulo, 
        cantidad, 
        fecha_hora,
        precio_total,
        gastos_envio
    ) VALUES (
        p_email_cliente,
        p_codigo_articulo,
        p_cantidad,
        NOW(),
        v_precio_unitario * p_cantidad,
        v_gastos_final
    );
    
    -- Actualizar el stock
    UPDATE articulos 
    SET stock = stock - p_cantidad 
    WHERE codigo = p_codigo_articulo;
    
    SELECT LAST_INSERT_ID() AS num_pedido;
END;

-- =============================================
-- Vista: vista_pedidos_pendientes
-- Muestra los pedidos pendientes de envío
-- =============================================
CREATE VIEW vista_pedidos_pendientes AS
SELECT 
    p.num_pedido,
    c.nombre AS nombre_cliente,
    c.email AS email_cliente,
    a.descripcion AS articulo,
    p.cantidad,
    p.precio_total,
    p.gastos_envio,
    p.fecha_hora
FROM 
    pedidos p
    JOIN clientes c ON p.email_cliente = c.email
    JOIN articulos a ON p.codigo_articulo = a.codigo
WHERE 
    p.enviado = FALSE
ORDER BY 
    p.fecha_hora ASC;
