package ojoaldato.controlador;

import ojoaldato.modelo.Pedido;
import java.util.List;
import ojoaldato.modelo.Datos;
import ojoaldato.modelo.Cliente;

/**
 * Clase PedidoControlador que actúa como intermediaria entre la vista
 * y la capa de datos para gestionar las operaciones relacionadas con los pedidos.
 */

public class PedidoControlador {
    private Datos  datos;

    public PedidoControlador(Datos datos) {
        this.datos = datos;
    }

    /**
     * Crea un nuevo pedido asociado a un cliente.
     *
     * @param cliente Cliente que realiza el pedido
     * @param pedido  Pedido a registrar
     * @return true si se crea correctamente, false si el cliente no existe
     */

    public boolean crearPedido(Cliente cliente, Pedido pedido) {
        if (datos.buscarCliente(cliente.getEmail()) != null) {  /* Aquí buscamos el clienete por su email para evitar crear pedidos para clientes que no existen */
            datos.crearPedido(cliente, pedido);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Lista todos los pedidos realizados por un cliente concreto.
     *
     * @param cliente Cliente cuyos pedidos se desean consultar
     * @return Lista de pedidos del cliente, o una lista vacía si no tiene
     */
    public List<Pedido> listarPedidosPorCliente(Cliente cliente) {
        return datos.listarPedidosPorCliente(cliente);
    }

    /**
     * Devuelve una lista con todos los pedidos registrados en el sistema.
     *
     * @return Lista de todos los pedidos
     */
    public List<Pedido> listarTodosPedidos() {
        return datos.listarTodosPedidos();
    }



}
