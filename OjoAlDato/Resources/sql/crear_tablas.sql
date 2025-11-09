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

