-- =============================================
-- Procedimiento: sp_actualizar_stock
-- Descripción: Actualiza el stock de un artículo de forma segura.
--              Evita que el stock sea negativo.
-- =============================================
DELIMITER //

-- Primero eliminamos el procedimiento si ya existe
DROP PROCEDURE IF EXISTS sp_actualizar_stock //

-- Luego lo creamos
CREATE PROCEDURE sp_actualizar_stock(
    IN p_codigo_articulo VARCHAR(20),
    IN p_cantidad INT
)
BEGIN
    -- Verificar si el artículo existe
    IF NOT EXISTS (SELECT 1 FROM articulos WHERE codigo = p_codigo_articulo) THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'El artículo no existe';
    END IF;
    
    -- Actualizar el stock si hay suficiente
    UPDATE articulos 
    SET stock = stock + p_cantidad
    WHERE codigo = p_codigo_articulo
    AND (stock + p_cantidad) >= 0;
    
    -- Si no se actualizó ninguna fila, es porque no hay stock suficiente
    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Stock insuficiente';
    END IF;
END //

-- Restauramos el delimitador
DELIMITER ;