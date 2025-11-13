package ojoaldato.DAO;

import ojoaldato.db.ConexionDB;
import ojoaldato.modelo.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {

    //Creación de un nuevo pedido en la base de datos
    @Override
    public boolean crear(Pedido pedido) {

        String sql = """
        INSERT INTO pedidos 
            (email_cliente, codigo_articulo, cantidad, fecha_hora, precio_total, gastos_envio) 
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, pedido.getCliente().getEmail());
            preparedStatement.setString(2, pedido.getArticulo().getCodigo());
            preparedStatement.setInt(3, pedido.getCantidad());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(pedido.getFechaHora()));
            preparedStatement.setBigDecimal(5, pedido.calcularImporteTotal());
            preparedStatement.setBigDecimal(6, pedido.getArticulo().getGastosEnvio());

            int filas = preparedStatement.executeUpdate();

            if (filas > 0) {
                // Recuperar el num_pedido generado automáticamente
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        pedido.setNumPedido(idGenerado);
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al crear el pedido: " + e.getMessage());
        }

        return false;
    }

    //Eliminación de un pedido en la base de datos si no está enviado
    @Override
    public boolean eliminar(Integer numPedido) {
        actualizarPedidosAutomaticamente();

        String sql = "DELETE FROM pedidos WHERE num_pedido = ? AND enviado = 0";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, numPedido);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el pedido: " + e.getMessage());
            return false;
        }
    }


    //STRING BASE PARA CONSULTAS CON JOIN

    private static final String BASE_QUERY = """
        SELECT p.num_pedido, p.cantidad, p.fecha_hora, p.enviado, p.fecha_envio, p.precio_total, p.gastos_envio AS pedido_gastos_envio,
               c.email AS cliente_email, c.nombre AS cliente_nombre, c.domicilio AS cliente_domicilio, c.nif AS cliente_nif, c.tipo AS cliente_tipo,
               a.codigo AS articulo_codigo, a.descripcion AS articulo_descripcion, a.pvp AS articulo_pvp, a.gastos_envio AS articulo_gastos_envio,
               a.tiempo_preparacion AS articulo_tiempo_preparacion, a.stock AS articulo_stock
        FROM pedidos p
        JOIN clientes c ON p.email_cliente = c.email
        JOIN articulos a ON p.codigo_articulo = a.codigo
    """;

    //CONSULTAS CON JOIN

    @Override
    public List<Pedido> obtenerTodos() {
        actualizarPedidosAutomaticamente();
        return listarPedidos(BASE_QUERY, null);
    }

    @Override
    public List<Pedido> buscarPorEmail(String email) {
        actualizarPedidosAutomaticamente();
        return listarPedidos(BASE_QUERY + " WHERE p.email_cliente = ?", email);
    }

    @Override
    public List<Pedido> obtenerEnviados() {
        actualizarPedidosAutomaticamente();
        return listarPedidos(BASE_QUERY + " WHERE p.enviado = 1", null);
    }

    @Override
    public List<Pedido> obtenerEnviadosPorEmail(String email) {
        actualizarPedidosAutomaticamente();
        return listarPedidos(BASE_QUERY + " WHERE p.enviado = 1 AND p.email_cliente = ?", email);
    }

    @Override
    public List<Pedido> obtenerPendientes() {
        actualizarPedidosAutomaticamente();
        return listarPedidos(BASE_QUERY + " WHERE p.enviado = 0", null);
    }

    @Override
    public List<Pedido> obtenerPendientesPorEmail(String email) {
        actualizarPedidosAutomaticamente();
        return listarPedidos(BASE_QUERY + " WHERE p.enviado = 0 AND p.email_cliente = ?", email);
    }

    @Override
    public Pedido buscar(Integer id) {
        actualizarPedidosAutomaticamente();
        List<Pedido> pedidos = listarPedidos(BASE_QUERY + " WHERE p.num_pedido = ?", id);
        return pedidos.isEmpty() ? null : pedidos.get(0);
    }

    @Override
    public boolean actualizar(Pedido pedido) {
        LocalDateTime ahora = LocalDateTime.now();
        if (pedido.esCancelable(ahora)) {
            return false;
        }
        String sql = "UPDATE pedidos SET enviado = 1, fecha_envio = NOW() WHERE num_pedido = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pedido.getNumPedido());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar pedido: " + e.getMessage());
            return false;
        }
    }


    @Override
    public void actualizarPedidosAutomaticamente() {
        String sql = """
        UPDATE pedidos p
        JOIN articulos a ON p.codigo_articulo = a.codigo
        SET p.enviado = 1, p.fecha_envio = NOW()
        WHERE p.enviado = 0
          AND p.fecha_hora <= DATE_SUB(NOW(), INTERVAL a.tiempo_preparacion MINUTE)
    """;
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int actualizados = ps.executeUpdate();
            if (actualizados > 0) {
                System.out.println("Pedidos actualizados automáticamente: " + actualizados);
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar pedidos automáticamente: " + e.getMessage());
        }
    }


    //MÉTODO AUXILIAR PARA LISTAR PEDIDOS

    private List<Pedido> listarPedidos(String sql, Object parametro) {
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (parametro != null) {
                if (parametro instanceof String) ps.setString(1, (String) parametro);
                else if (parametro instanceof Integer) ps.setInt(1, (Integer) parametro);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cliente cliente;
                    if ("PREMIUM".equalsIgnoreCase(rs.getString("cliente_tipo"))) {
                        cliente = new ClientePremium(
                                rs.getString("cliente_nombre"),
                                rs.getString("cliente_domicilio"),
                                rs.getString("cliente_nif"),
                                rs.getString("cliente_email"),
                                BigDecimal.valueOf(30.0),
                                BigDecimal.valueOf(0.8)
                        );
                    } else {
                        cliente = new ClienteEstandar(
                                rs.getString("cliente_nombre"),
                                rs.getString("cliente_domicilio"),
                                rs.getString("cliente_nif"),
                                rs.getString("cliente_email")
                        );
                    }

                    Articulo articulo = new Articulo(
                            rs.getString("articulo_codigo"),
                            rs.getString("articulo_descripcion"),
                            rs.getBigDecimal("articulo_pvp"),
                            rs.getBigDecimal("articulo_gastos_envio"),
                            rs.getInt("articulo_tiempo_preparacion"),
                            rs.getInt("articulo_stock")
                    );

                    Pedido pedido = new Pedido(
                            rs.getInt("num_pedido"),
                            cliente,
                            articulo,
                            rs.getInt("cantidad"),
                            rs.getTimestamp("fecha_hora").toLocalDateTime()
                    );

                    pedidos.add(pedido);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al listar los pedidos: " + e.getMessage());
        }

        return pedidos;
    }
}