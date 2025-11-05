package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.ClientePremium;
import Util.ConexionDB;


public interface ClienteDAO extends IDAO<Cliente> {

    boolean existe(String email) throws SQLException;

    // Implementación interna del DAO
    class ClienteDAOImpl implements ClienteDAO {

        // --- Sentencias SQL ---
        private static final String SQL_INSERT = """
            INSERT INTO clientes
                (email, nombre, domicilio, nif, tipo, cuota, descuento_envio, fecha_alta, activo)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        private static final String SQL_SELECT_ONE = """
            SELECT email, nombre, domicilio, nif, tipo, cuota, descuento_envio, fecha_alta, activo
            FROM clientes
            WHERE email = ?
        """;

        private static final String SQL_SELECT_ALL = """
            SELECT email, nombre, domicilio, nif, tipo, cuota, descuento_envio, fecha_alta, activo
            FROM clientes
            ORDER BY nombre ASC
        """;

        private static final String SQL_UPDATE = """
            UPDATE clientes
            SET nombre = ?, domicilio = ?, nif = ?, tipo = ?, cuota = ?, descuento_envio = ?, activo = ?
            WHERE email = ?
        """;

        private static final String SQL_DELETE = """
            DELETE FROM clientes
            WHERE email = ?
        """;

        private static final String SQL_EXISTS = """
            SELECT 1 FROM clientes WHERE email = ? LIMIT 1
        """;

        // ---------- Métodos CRUD ----------

        @Override
        public void insertar(Cliente c) throws SQLException {
            try (Connection con = ConexionDB.getConexion();
                 PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

                ps.setString(1, c.getEmail());
                ps.setString(2, c.getNombre());
                ps.setString(3, c.getDomicilio());
                ps.setString(4, c.getNif());
                ps.setString(5, c.getTipo()); // ESTANDAR o PREMIUM
                ps.setBigDecimal(6, c.getCuota());
                ps.setBigDecimal(7, c.getDescuentoEnvio());
                ps.setTimestamp(8, Timestamp.valueOf(c.getFechaAlta())); // ya lo pasas desde el modelo
                ps.setBoolean(9, c.isActivo());
                ps.executeUpdate();
            }
        }

        @Override
        public Cliente buscar(String email) throws SQLException {
            if (email == null || email.isBlank()) return null;

            try (Connection con = ConexionDB.getConexion();
                 PreparedStatement ps = con.prepareStatement(SQL_SELECT_ONE)) {

                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearCliente(rs);
                    }
                }
            }
            return null;
        }

        @Override
        public List<Cliente> listar() throws SQLException {
            List<Cliente> lista = new ArrayList<>();

            try (Connection con = ConexionDB.getConexion();
                 PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearCliente(rs));
                }
            }
            return lista;
        }

        @Override
        public void actualizar(Cliente c) throws SQLException {
            try (Connection con = ConexionDB.getConexion();
                 PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {

                ps.setString(1, c.getNombre());
                ps.setString(2, c.getDomicilio());
                ps.setString(3, c.getNif());
                ps.setString(4, c.getTipo());
                ps.setBigDecimal(5, c.getCuota());
                ps.setBigDecimal(6, c.getDescuentoEnvio());
                ps.setBoolean(7, c.isActivo());
                ps.setString(8, c.getEmail());
                ps.executeUpdate();
            }
        }

        @Override
        public void eliminar(String email) throws SQLException {
            try (Connection con = ConexionDB.getConexion();
                 PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {
                ps.setString(1, email);
                ps.executeUpdate();
            }
        }

        @Override
        public boolean existe(String email) throws SQLException {
            try (Connection con = ConexionDB.getConexion();
                 PreparedStatement ps = con.prepareStatement(SQL_EXISTS)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            }
        }

        // ---------- Métodos auxiliares ----------

        private Cliente mapearCliente(ResultSet rs) throws SQLException {
            String email = rs.getString("email");
            String nombre = rs.getString("nombre");
            String domicilio = rs.getString("domicilio");
            String nif = rs.getString("nif");
            String tipo = rs.getString("tipo");
            var cuota = rs.getBigDecimal("cuota");
            var descuento = rs.getBigDecimal("descuento_envio");
            var fechaAlta = rs.getTimestamp("fecha_alta").toLocalDateTime();
            boolean activo = rs.getBoolean("activo");

            Cliente c;
            if ("PREMIUM".equalsIgnoreCase(tipo)) {
                c = new ClientePremium(email, nombre, domicilio, nif, cuota, descuento);
            } else {
                c = new ClienteEstandar(email, nombre, domicilio, nif);
            }

            c.setFechaAlta(fechaAlta);
            c.setActivo(activo);
            return c;
        }
    }
}