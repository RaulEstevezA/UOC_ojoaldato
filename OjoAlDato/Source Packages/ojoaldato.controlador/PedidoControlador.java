package ojoaldato.controlador;

import ojoaldato.modelo.Pedido;
import java.util.List;
import ojoaldato.modelo.Datos;

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



}
