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