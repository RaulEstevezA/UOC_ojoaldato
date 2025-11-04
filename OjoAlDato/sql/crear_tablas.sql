-- Crear la base de datos (si no existe)
CREATE DATABASE IF NOT EXISTS ojoaldato;
USE ojoaldato;

-- Tabla de Clientes (común para estándar y premium)
CREATE TABLE clientes (
    email VARCHAR(100) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    domicilio VARCHAR(200) NOT NULL,
    nif VARCHAR(20) NOT NULL UNIQUE,
    tipo ENUM('ESTANDAR', 'PREMIUM') NOT NULL,
    cuota DECIMAL(10,2) DEFAULT 0.00,
    descuento_envio DECIMAL(5,2) DEFAULT 0.00,
    fecha_alta DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    CONSTRAINT chk_email CHECK (email LIKE '%@%.%')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de Artículos
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
    CONSTRAINT chk_gastos_envio_positivos CHECK (gastos_envio >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de Pedidos
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

-- Procedimiento almacenado para crear un nuevo pedido
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
    
    -- Obtener datos del artículo
    SELECT pvp, gastos_envio 
    INTO v_precio_unitario, v_gastos_envio
    FROM articulos 
    WHERE codigo = p_codigo_articulo AND activo = TRUE;
    
    -- Obtener tipo de cliente y descuento
    SELECT tipo, 
           CASE WHEN tipo = 'PREMIUM' THEN descuento_envio ELSE 0 END
    INTO v_tipo_cliente, v_descuento
    FROM clientes 
    WHERE email = p_email_cliente AND activo = TRUE;
    
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
    
    SELECT LAST_INSERT_ID() AS num_pedido;
END //
DELIMITER ;

-- Vista para ver los pedidos pendientes
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

-- Insertar datos de prueba (opcional)
INSERT INTO clientes (nombre, domicilio, nif, email, tipo, descuento_envio) VALUES
('Cliente Estándar', 'Calle Falsa 123', '12345678A', 'cliente1@ejemplo.com', 'ESTANDAR', 0),
('Cliente Premium', 'Avenida Real 456', '87654321B', 'cliente2@ejemplo.com', 'PREMIUM', 20);

INSERT INTO articulos (codigo, descripcion, pvp, gastos_envio, tiempo_preparacion, stock) VALUES
('ART001', 'Artículo de prueba 1', 19.99, 4.99, 24, 100),
('ART002', 'Artículo de prueba 2', 49.99, 7.99, 48, 50);

-- Llamar al procedimiento para crear un pedido de prueba
CALL crear_pedido('cliente1@ejemplo.com', 'ART001', 2);
CALL crear_pedido('cliente2@ejemplo.com', 'ART002', 1);