package ojoaldato.DAO;

import ojoaldato.db.ConexionDB;
import ojoaldato.modelo.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {

    // Creación de un nuevo pedido en la base de datos
    @Override
    public boolean crear(Pedido pedido) {
        String sql = " INSERT INTO pedidos (num_pedido, email_cliente, codigo_articulo, cantidad, fecha_hora, precio_total, gastos_envio) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ojoaldato.db.ConexionDB.getConnection();
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
        String sql = " DELETE FROM pedidos WHERE num_pedido = ? AND enviado = 0";

        try (Connection connection = ojoaldato.db.ConexionDB.getConnection();
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

        try (Connection connection = ojoaldato.db.ConexionDB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                pedidos.add(mapearPedido(resultSet));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar los pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    //Búsqueda de pedidos por mail de cliente
    @Override
    public List<Pedido> buscarPorEmail(String email) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE email_cliente = ?";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    pedidos.add(mapearPedido(resultSet));
                }
            }

        }catch (SQLException e) {
            System.err.println("Error al obtener los pedidos por mail: " + e.getMessage());
        }
        return pedidos;
    }

    //Lista de pedidos enviados
    @Override
    public List<Pedido> obtenerEnviados() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE enviado = 1";

        try (Connection connection = ConexionDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                pedidos.add(mapearPedido(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los pedidos enviados " + e.getMessage());
        }
        return pedidos;
    }

    //Lista de pedidos enviados por cliente
    @Override
    public List<Pedido> obtenerEnviadosPorEmail(String email) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE enviado = 1 AND email_cliente = ?";

        try (Connection connection = ConexionDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    pedidos.add(mapearPedido(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los pedidos enviados: " + e.getMessage());
        }
        return pedidos;
    }

    //Listar todos los pedidos pendientes
    @Override
    public List<Pedido> obtenerPendientes() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE enviado = 0";
        try (Connection connection = ConexionDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                pedidos.add(mapearPedido(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los pedidos pendientes: " + e.getMessage());
        }
        return pedidos;
    }

    //Listar pedidos pendientes filtrando por cliente
    @Override
    public List<Pedido> obtenerPendientesPorEmail(String email) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE enviado = 0 AND email_cliente = ?";

        try (Connection connection = ConexionDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    pedidos.add(mapearPedido(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los pedidos pendientes por email: " + e.getMessage());
        }
        return pedidos;
    }

    //Búsqueda de pedido por número de pedido (no solicitado en rúbrica).
    @Override
    public Pedido buscar(Integer id) {
        String sql = "SELECT * FROM pedidos WHERE num_pedido = ?";
        try (Connection connection = ConexionDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearPedido(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el pedido: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean actualizar(Pedido pedido) {
        return false;
    }

    //Método auxiliar para mapear el objeto Pedido usando consultas JOIN
    private Pedido mapearPedido(ResultSet resultSet) throws SQLException {

        //Datos del pedido
        int numPedido = resultSet.getInt("num_pedido");
        int cantidad = resultSet.getInt("cantidad");
        LocalDateTime fechaHora = resultSet.getTimestamp("fecha_hora").toLocalDateTime();

        //Datos del cliente
        String emailCliente = resultSet.getString("email_cliente");

        String tipoCliente = "ESTANDAR";
        try {
            tipoCliente = resultSet.getString("tipo_cliente");
            if(tipoCliente == null) tipoCliente = "ESTANDAR";
        } catch (SQLException ignored) {
        }

        Cliente cliente;
        if("PREMIUM".equalsIgnoreCase(tipoCliente)) {
            cliente = new ClientePremium("", "", "", emailCliente, BigDecimal.valueOf(30.00), BigDecimal.valueOf(0.8));
        } else {
            cliente = new ClienteEstandar("", "", "", emailCliente);
        }

        //Datos del artículo
        String codigoArticulo = resultSet.getString("codigo_articulo");
        Articulo articulo = new Articulo(codigoArticulo, "", BigDecimal.ZERO, BigDecimal.ZERO, 0);

        return new Pedido(numPedido, cliente, articulo, cantidad, fechaHora);
    }
}