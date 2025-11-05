// ClienteDAO.java

/**
 * Clase DAO para gestionar clientes en la base de datos.
 * Implementa directamente las operaciones CRUD sobre la tabla 'clientes'.
 */

package DAO;

import Util.ConexionDB;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.ClientePremium;


public class ClienteDAO {

    // Consultas SQL
    private static final String INSERTAR_CLIENTE = """
        INSERT INTO clientes
            (email, nombre, domicilio, nif, tipo, cuota, descuento_envio)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

    private static final String SELECCIONAR_CLIENTE = """
        SELECT email, nombre, domicilio, nif, tipo, cuota, descuento_envio, fecha_alta, activo
        FROM clientes
        WHERE email = ?
    """;

    private static final String SELECCIONAR_TODOS_CLIENTES = """
        SELECT email, nombre, domicilio, nif, tipo, cuota, descuento_envio, fecha_alta, activo
        FROM clientes
        ORDER BY nombre ASC
    """;

    private static final String MODIFICAR_CLIENTE = """
        UPDATE clientes
        SET nombre = ?, domicilio = ?, nif = ?, tipo = ?, cuota = ?, descuento_envio = ?
        WHERE email = ?
    """;

    private static final String ELIMINAR_CLIENTE = """
        DELETE FROM clientes
        WHERE email = ?
    """;

    private static final String EXISTE_CLIENTE = """
        SELECT 1 FROM clientes WHERE email = ? LIMIT 1
    """;

    // Métodos CRUD

    // Crear Cliente
    public boolean crear(Cliente c) {
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERTAR_CLIENTE)) {

            ps.setString(1, c.getEmail());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDomicilio());
            ps.setString(4, c.getNif());

            // tipo según clase
            if (c instanceof ClientePremium) {
                ps.setString(5, "PREMIUM");
            } else {
                ps.setString(5, "ESTANDAR");
            }
            ps.setBigDecimal(6, c.getCuota());
            if (c instanceof ClientePremium) {
                ps.setBigDecimal(7, new BigDecimal("0.8"));
            } else {
                ps.setBigDecimal(7, new BigDecimal("1.0"));
            }

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al crear cliente: " + e.getMessage());
            return false;
        }
    }

    // Buscar Cliente
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

    // Actualizar Cliente
    public boolean actualizar(Cliente c) {
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(MODIFICAR_CLIENTE)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDomicilio());
            ps.setString(3, c.getNif());
            ps.setString(4, (c instanceof ClientePremium) ? "PREMIUM" : "ESTANDAR");
            ps.setBigDecimal(5, c.getCuota());
            if (c instanceof ClientePremium) {
                ps.setBigDecimal(6, new BigDecimal("0.8"));
            } else {
                ps.setBigDecimal(6, new BigDecimal("1.0"));
            }
            ps.setString(7, c.getEmail());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // Eliminar Cliente
    public boolean eliminar(String email) {
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(ELIMINAR_CLIENTE)) {

            ps.setString(1, email);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    // Obtener todos los clientes
    public List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECCIONAR_TODOS_CLIENTES);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    // Existe el cliente?
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

    //  Metodo auxiliar
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        String email = rs.getString("email");
        String nombre = rs.getString("nombre");
        String domicilio = rs.getString("domicilio");
        String nif = rs.getString("nif");
        String tipo = rs.getString("tipo");
        var cuota = rs.getBigDecimal("cuota");
        var descuento = rs.getBigDecimal("descuento_envio");

        Cliente c;
        if ("PREMIUM".equalsIgnoreCase(tipo)) {
            c = new ClientePremium(email, nombre, domicilio, nif, cuota, descuento);
        } else {
            c = new ClienteEstandar(email, nombre, domicilio, nif);
        }

        return c;
    }
}