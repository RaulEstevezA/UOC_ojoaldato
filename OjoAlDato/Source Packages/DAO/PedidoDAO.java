package DAO;

import Util.ConexionDB;
import ojoaldato.modelo.Pedido;

import java.sql.*;

public class PedidoDAO implements IDAO<Pedido, Integer> {

    @Override
    public boolean crear(Pedido pedido) {
        String sql = " INSERT INTO pedidos (num_pedido, email_cliente, codigo_articulo, cantidad, fecha_hora, precio_total, gastos_envio) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, pedido.getNumPedido());
            preparedStatement.setString(2, pedido.getCliente().getEmail());
            preparedStatement.setString(3, pedido.getArticulo().getCodigo());
            preparedStatement.setInt(4, pedido.getCantidad());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(pedido.getFechaHora()));
            preparedStatement.setBigDecimal(6, pedido.calcularImporteTotal());
            preparedStatement.setBigDecimal(7, pedido.getArticulo().getGastosEnvio());

            int filas = preparedStatement.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al crear el pedido: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Pedido pedido) {
        String sql = " UPDATE pedidos SET email_cliente = ?, codigo_articulo = ?, cantidad = ?, fecha_hora = ?, precio_total = ?, gastos_envio = ? WHERE num_pedido = ? ";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, pedido.getCliente().getEmail());
            preparedStatement.setString(2, pedido.getArticulo().getCodigo());
            preparedStatement.setInt(3, pedido.getCantidad());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(pedido.getFechaHora()));
            preparedStatement.setBigDecimal(5, pedido.calcularImporteTotal());
            preparedStatement.setBigDecimal(6, pedido.getArticulo().getGastosEnvio());
            preparedStatement.setInt(7, pedido.getNumPedido());

            int filas = preparedStatement.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el pedido: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(Integer numPedido) {
        String sql = " DELETE FROM pedidos WHERE num_pedido = ? ";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, numPedido);
            int filas = preparedStatement.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el pedido: " + e.getMessage());
            return false;
        }
    }
}