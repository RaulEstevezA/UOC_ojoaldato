INSERT INTO clientes (nombre, domicilio, nif, email, tipo, descuento_envio) VALUES
('Cliente Estándar', 'Calle Falsa 123', '12345678A', 'cliente1@ejemplo.com', 'ESTANDAR', 0),
('Cliente Premium', 'Avenida Real 456', '87654321B', 'cliente2@ejemplo.com', 'PREMIUM', 20);

INSERT INTO articulos (codigo, descripcion, pvp, gastos_envio, tiempo_preparacion, stock) VALUES
('ART001', 'Artículo de prueba 1', 19.99, 4.99, 24, 100),
('ART002', 'Artículo de prueba 2', 49.99, 7.99, 48, 50);

CALL crear_pedido('cliente1@ejemplo.com', 'ART001', 2);
CALL crear_pedido('cliente2@ejemplo.com', 'ART002', 1);