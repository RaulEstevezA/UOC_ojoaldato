package ojoaldato.DAO;

import ojoaldato.db.ConexionDB;
import ojoaldato.modelo.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class PedidoDAO implements IDAO<Pedido, Integer> {

    // Creación de un nuevo pedido en la base de datos
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

    //Eliminación de un pedido en la base de datos
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

    //Listar todos los pedidos de la base de datos
    @Override
    public List<Pedido> obtenerTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        try (Connection connection = ConexionDB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("LISTADO DE PEDIDOS");
            while (resultSet.next()) {
                System.out.println("Número de pedido: " + resultSet.getInt("num_pedido"));
                System.out.println("Email cliente: " + resultSet.getString("email_cliente"));
                System.out.println("Código artículo: " + resultSet.getString("codigo_articulo"));
                System.out.println("Cantidad: " + resultSet.getInt("cantidad"));
                System.out.println("Fecha y hora: " + resultSet.getTimestamp("fecha_hora"));
                System.out.println("Precio total: " + resultSet.getBigDecimal("precio_total"));
                System.out.println("Gastos de envío: " + resultSet.getBigDecimal("gastos_envio"));
                System.out.println("----------------------------------------");
            }

        } catch (SQLException e) {
            System.err.println("Error al listar los pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    //Búsqueda de pedidos por mail de cliente
    @Override
    public Pedido buscarPorEmail(String email) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE email_cliente = ?";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Pedidos del cliente con email: " + email);

            while (resultSet.next()) {
                System.out.println("Número de pedido: " + resultSet.getInt("num_pedido"));
                System.out.println("Código artículo: " + resultSet.getString("codigo_articulo"));
                System.out.println("Cantidad: " + resultSet.getInt("cantidad"));
                System.out.println("Fecha y hora: " + resultSet.getTimestamp("fecha_hora"));
                System.out.println("Precio total: " + resultSet.getBigDecimal("precio_total"));
            }

        }catch (SQLException e) {
            System.err.println("Error al obtener los pedidos por mail: " + e.getMessage());
        }
        return null;
    }
}