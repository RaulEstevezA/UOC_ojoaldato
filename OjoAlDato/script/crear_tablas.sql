-- =============================================
-- Script de creación de la base de datos OjoAlDato
-- =============================================

-- Borrar tabla si existe
DROP DATABASE IF EXISTS ojoaldato;

-- Crear la base de datos (si no existe)
CREATE DATABASE IF NOT EXISTS ojoaldato 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_spanish2_ci;

USE ojoaldato;

-- Establecer la collation por defecto para la sesión
SET NAMES utf8mb4 COLLATE utf8mb4_spanish2_ci;

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
    DECLARE v_gastos_envio DECIMAL(10,2);
    DECLARE v_tipo_cliente ENUM('ESTANDAR', 'PREMIUM');
    DECLARE v_descuento DECIMAL(5,2);
    
    -- Verificar si el cliente existe y está activo
    IF NOT EXISTS (SELECT 1 FROM clientes WHERE email = p_email_cliente AND activo = TRUE) THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Cliente no encontrado o inactivo';
    END IF;
    
    -- Verificar si el artículo existe, está activo y hay stock suficiente
    SELECT pvp, gastos_envio, stock >= p_cantidad
    INTO v_precio_unitario, v_gastos_envio, @stock_suficiente
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
    
    -- Obtener tipo de cliente y descuento
    SELECT tipo, 
           CASE WHEN tipo = 'PREMIUM' THEN descuento_envio ELSE 0 END
    INTO v_tipo_cliente, v_descuento
    FROM clientes 
    WHERE email = p_email_cliente;
    
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
        v_gastos_envio * (1 - v_descuento/100) * p_cantidad
    );
    
    -- Actualizar el stock
    UPDATE articulos 
    SET stock = stock - p_cantidad 
    WHERE codigo = p_codigo_articulo;
    
    SELECT LAST_INSERT_ID() AS num_pedido;
END //
DELIMITER ;

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

-- =============================================
-- Datos de prueba (opcional)

-- ==========================================
-- CLIENTES DE PRUEBA
-- ==========================================
INSERT INTO clientes (email, nombre, domicilio, nif, tipo)
VALUES
('cliente1@ejemplo.com', 'Cliente Estándar', 'Calle Falsa 123', '12345678A', 'ESTANDAR'),
('cliente2@ejemplo.com', 'Cliente Premium', 'Avenida Real 456', '87654321B', 'PREMIUM');

-- ==========================================
-- ARTÍCULOS DE PRUEBA
-- ==========================================
INSERT INTO articulos (codigo, descripcion, pvp, gastos_envio, tiempo_preparacion, stock)
VALUES
('ART001', 'Artículo de prueba 1', 19.99, 4.99, 24, 100),
('ART002', 'Artículo de prueba 2', 49.99, 7.99, 48, 50);

-- ==========================================
-- PEDIDOS DE PRUEBA
-- ==========================================
INSERT INTO pedidos (email_cliente, codigo_articulo, cantidad, fecha_hora, precio_total, gastos_envio)
VALUES
('cliente1@ejemplo.com', 'ART001', 2, NOW(), 19.99 * 2, 4.99),
('cliente2@ejemplo.com', 'ART002', 1, NOW(), 49.99, 7.99);


