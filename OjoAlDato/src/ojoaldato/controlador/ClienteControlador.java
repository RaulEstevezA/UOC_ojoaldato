package ojoaldato.controlador;

import ojoaldato.DAO.ClienteDAO;
import ojoaldato.DAO.FactoryDAO;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.ClientePremium;

import java.util.Collections;
import java.util.List;

public class ClienteControlador {

    // DAO obtenido desde FactoryDAO
    private final ClienteDAO clienteDAO;

    public ClienteControlador() {
        this.clienteDAO = FactoryDAO.getClienteDAO();
    }

    // ==============================
    // Métodos de gestión de clientes
    // ==============================

    public String addCliente(Cliente c) {
        boolean creado = clienteDAO.crear(c);

        if (creado) {
            return "Cliente con email " + c.getEmail() +" añadido correctamente.";
        } else {
            return "No se pudo añadir el cliente (posible duplicado o error de BD).";
        }
    }

    public String deleteCliente(String email) {
        boolean eliminado = clienteDAO.eliminar(email);

        if (eliminado) {
            return "Cliente con email " + email + " desactivado correctamente.";
        } else {
            return "No se pudo desactivar el cliente (puede que no exista).";
        }
    }

    public Cliente buscarCliente(String email) {
        return clienteDAO.buscar(email);
    }

    public List<Cliente> listarClientes() {
        try {
            return clienteDAO.obtenerTodos();
        } catch (Exception e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Cliente> listarClientesEstandar() {
        try {
            return clienteDAO.obtenerEstandar();
        } catch (Exception e) {
            System.err.println("Error al listar clientes estándar: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Cliente> listarClientesPremium() {
        try {
            return clienteDAO.obtenerPremium();
        } catch (Exception e) {
            System.err.println("Error al listar clientes premium: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}