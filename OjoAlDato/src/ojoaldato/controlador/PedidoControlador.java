package ojoaldato.controlador;

import ojoaldato.excepcion.ElementoNoEncontradoException;
import ojoaldato.excepcion.PedidoInvalidoException;
import ojoaldato.modelo.Pedido;

import java.util.Collections;
import java.util.List;
import ojoaldato.modelo.Datos;
import ojoaldato.modelo.Cliente;

/**
 * Clase PedidoControlador que actúa como intermediaria entre la ojoaldato.vista
 * y la capa de datos para gestionar las operaciones relacionadas con los pedidos.
 */

public class PedidoControlador {
    private final static Datos datos = new Datos();

    /**
     * Crea un nuevo pedido asociado a un cliente.
     *
     * @param c Cliente que realiza el pedido
     * @param p  Pedido a registrar
     * @return Mensaje con el resultado de la operación
     */

    public String addPedido(Cliente c, Pedido p) {
        try {
            datos.crearPedido(c, p);
            return "Pedido creado correctamente para el cliente " + c.getEmail();
        } catch (PedidoInvalidoException | ElementoNoEncontradoException e) {
          return "No se pudo crear el pedido: " + e.getMessage();
        } catch (Exception e) {
            return "Error inesperado al crear el pedido " + p.getNumPedido() + ".\n" + e.getMessage();
        }
    }

    public String deletePedido(int numPedido) {
        try {
            datos.eliminarPedido(numPedido);
            return "Pedido eliminado correctamente.";
        } catch (PedidoInvalidoException | ElementoNoEncontradoException e) {
            return "No se pudo eliminar el pedido: " + e.getMessage();
        } catch (Exception e) {
            return "Error inesperado al eliminar el pedido " + numPedido + ".\n" + e.getMessage();
        }
    }

    /**
     * Lista todos los pedidos realizados por un cliente concreto.
     *
     * @param cliente Cliente cuyos pedidos se desean consultar
     * @return Lista de pedidos del cliente, o una lista vacía si no tiene
     */
    public List<Pedido> listarPedidosPorCliente(Cliente cliente) {
        try {
            return datos.listarPedidosPorCliente(cliente);
        } catch (ElementoNoEncontradoException e) {
            System.err.println("Aviso: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Devuelve una lista con todos los pedidos registrados en el sistema.
     *
     * @return Lista de todos los pedidos o lista vacía si no existen registros de pedidos
     */
    public List<Pedido> listarTodosPedidos() {
        try {
            return datos.listarTodosPedidos();
        } catch (ElementoNoEncontradoException e) {
            System.err.println("Aviso: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Lista todos los pedidos realizados por clientes estándar.
     *
     * @return Lista de pedidos de clientes estándar o lista vacía si no hay
     */
    public List<Pedido> listarPedidosClientesEstandar() {
        try {
            return datos.listarPedidosClientesEstandar();
        } catch (ElementoNoEncontradoException e) {
            System.err.println("Aviso: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Lista todos los pedidos realizados por clientes premium.
     *
     * @return Lista de pedidos de clientes premium o lista vacía si no hay
     */
    public List<Pedido> listarPedidosClientesPremium() {
        try {
            return datos.listarPedidosClientesPremium();
        } catch (ElementoNoEncontradoException e) {
            System.err.println("Aviso: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Pedido> listarPedidosPendientes(String email) {
        return datos.listarPedidosPendientes(email);
    }

    public List<Pedido> listarPedidosEnviados(String email) {
        return datos.listarPedidosEnviados(email);
    }
}
