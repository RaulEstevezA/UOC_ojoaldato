package ojoaldato.controlador;

import ojoaldato.DAO.FactoryDAO;
import ojoaldato.DAO.PedidoDAO;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.Pedido;

import java.util.Collections;
import java.util.List;

public class PedidoControlador {

    private final PedidoDAO pedidoDAO;

    public PedidoControlador() {
        this.pedidoDAO = FactoryDAO.getPedidoDAO();
    }

    // ======================
    // Crear pedido
    // ======================
    public String addPedido(Cliente cliente, Pedido pedido) {
        try {
            boolean creado = pedidoDAO.crear(pedido);

            if (creado) {
                return "Pedido creado correctamente para el cliente "
                        + cliente.getEmail()
                        + ". Número asignado: " + pedido.getNumPedido();
            } else {
                return "No se pudo crear el pedido (posible error en BD).";
            }

        } catch (Exception e) {
            return "Error inesperado al crear el pedido: " + e.getMessage();
        }
    }

    // ======================
    // Eliminar pedido
    // ======================
    public String deletePedido(int numPedido) {
        try {
            boolean eliminado = pedidoDAO.eliminar(numPedido);

            if (eliminado) {
                return "Pedido eliminado correctamente.";
            } else {
                return "No se pudo eliminar el pedido. Puede que ya esté enviado.";
            }

        } catch (Exception e) {
            return "Error inesperado al eliminar el pedido: " + e.getMessage();
        }
    }

    // ======================
    // Listar por cliente
    // ======================
    public List<Pedido> listarPedidosPorCliente(String emailCliente) {
        try {
            if (emailCliente == null) {
                return Collections.emptyList();
            }

            return pedidoDAO.buscarPorEmail(emailCliente);

        } catch (Exception e) {
            System.err.println("Error al listar pedidos del cliente: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // ======================
    // Listar todos
    // ======================
    public List<Pedido> listarTodosPedidos() {
        try {
            return pedidoDAO.obtenerTodos();
        } catch (Exception e) {
            System.err.println("Error al listar todos los pedidos: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // ======================
    // Pendientes
    // ======================
    public List<Pedido> listarPedidosPendientes(String email) {
        try {
            // Sin filtro → todos los pendientes
            if (email == null || email.isBlank()) {
                return pedidoDAO.obtenerPendientes();
            }

            // Con filtro
            return pedidoDAO.obtenerPendientesPorEmail(email);

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // ======================
    // Enviados
    // ======================
    public List<Pedido> listarPedidosEnviados(String email) {
        try {
            // Si no se especifica email → traer todos
            if (email == null || email.isBlank()) {
                return pedidoDAO.obtenerEnviados();
            }

            // Si hay email, filtrado normal
            return pedidoDAO.obtenerEnviadosPorEmail(email);

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
