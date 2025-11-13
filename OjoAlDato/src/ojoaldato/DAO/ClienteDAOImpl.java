package ojoaldato.DAO;

import ojoaldato.db.ConexionDB;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.ClientePremium;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta del DAO para la entidad Cliente.
 * Gestiona las operaciones CRUD sobre la tabla 'clientes' en MySQL.
 */
public class ClienteDAOImpl implements ClienteDAO {

    // ========================
    // CONSULTAS SQL
    // ========================
    private static final String INSERTAR_CLIENTE = """
        INSERT INTO clientes
            (email, nombre, domicilio, nif, tipo)
        VALUES (?, ?, ?, ?, ?)
    """;

    private static final String SELECCIONAR_CLIENTE = """
        SELECT email, nombre, domicilio, nif, tipo, fecha_alta, activo
        FROM clientes
        WHERE email = ?
    """;

    private static final String SELECCIONAR_TODOS = """
        SELECT email, nombre, domicilio, nif, tipo, fecha_alta, activo
        FROM clientes
        ORDER BY nombre ASC
    """;

    private static final String MODIFICAR_CLIENTE = """
        UPDATE clientes
        SET nombre = ?, domicilio = ?, nif = ?, tipo = ?
        WHERE email = ?
    """;

    private static final String ELIMINAR_CLIENTE = """
        UPDATE clientes
        SET activo = FALSE
        WHERE email = ?
    """;

    private static final String EXISTE_CLIENTE = """
        SELECT 1 FROM clientes WHERE email = ? LIMIT 1
    """;

    // MÉTODOS CRUD (IDAO)
    @Override
    public boolean crear(Cliente c) {
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERTAR_CLIENTE)) {

            ps.setString(1, c.getEmail());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDomicilio());
            ps.setString(4, c.getNif());
            ps.setString(5, (c instanceof ClientePremium) ? "PREMIUM" : "ESTANDAR");

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al crear cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Cliente buscar(String email) {
        if (email == null || email.isBlank()) return null;

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECCIONAR_CLIENTE)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean actualizar(Cliente c) {
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(MODIFICAR_CLIENTE)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDomicilio());
            ps.setString(3, c.getNif());
            ps.setString(4, (c instanceof ClientePremium) ? "PREMIUM" : "ESTANDAR");
            ps.setString(5, c.getEmail());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(String email) {

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(ELIMINAR_CLIENTE)) {

            ps.setString(1, email);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al desactivar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECCIONAR_TODOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    // MÉTODOS ESPECÍFICOS
    @Override
    public List<Cliente> obtenerEstandar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = """
            SELECT email, nombre, domicilio, nif, tipo
            FROM clientes
            WHERE tipo = 'ESTANDAR'
            ORDER BY nombre ASC
        """;

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar clientes estándar: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Cliente> obtenerPremium() {
        List<Cliente> lista = new ArrayList<>();
        String sql = """
            SELECT email, nombre, domicilio, nif, tipo
            FROM clientes
            WHERE tipo = 'PREMIUM'
            ORDER BY nombre ASC
        """;

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar clientes premium: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public boolean existe(String email) {
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(EXISTE_CLIENTE)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Error al comprobar existencia: " + e.getMessage());
            return false;
        }
    }

    // MÉTODO AUXILIAR
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        String email = rs.getString("email");
        String nombre = rs.getString("nombre");
        String domicilio = rs.getString("domicilio");
        String nif = rs.getString("nif");
        String tipo = rs.getString("tipo");

        if ("PREMIUM".equalsIgnoreCase(tipo)) {
            return new ClientePremium(nombre, domicilio, nif, email,
                    BigDecimal.valueOf(30.00), BigDecimal.valueOf(0.8));
        } else {
            return new ClienteEstandar(nombre, domicilio, nif, email);
        }
    }
}
