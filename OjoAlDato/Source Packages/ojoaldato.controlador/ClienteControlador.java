package ojoaldato.controlador;

import ojoaldato.exception.ElementoDuplicadoException;
import ojoaldato.exception.ElementoNoEncontradoException;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.Datos;
import ojoaldato.modelo.Pedido;

import java.util.*;


/**
 * Clase controladora encargada de gestionar las operaciones relacionadas con los clientes.
 * Forma parte de la capa CONTROLADOR del patrón MVC.
 *
 * Su función principal es servir de intermediario entre la VISTA (interfaz del usuario)
 * y el MODELO (clase Datos), donde realmente se almacenan los datos.
 *
 * No guarda los clientes directamente, sino que delega las operaciones en la clase Datos.
 */

public class ClienteControlador {
    private Datos datos = new Datos();

    public ClienteControlador(Datos datos) {}

    // ==============================
    // Métodos de gestión de clientes
    // ==============================

    /**
     * Agrega un nuevo cliente al sistema si no existe otro con el mismo email.
     *
     * El email se utiliza como identificador único del cliente, ya que no puede haber
     * dos clientes con el mismo correo electrónico.
     *
     * @param c Cliente que se desea agregar
     * @return true si se agregó correctamente, false si ya existía un cliente con ese email
     */
    public String addCliente(Cliente c) {
        try {
            datos.agregarCliente(c);
            return "Cliente con email " + c.getEmail() +" añadido correctamente.";
        } catch (ElementoDuplicadoException e) {
            return "No se pudo añadir el cliente. Ya existe un cliente con ese email.";
        } catch (Exception e) {
            return "Error inesperado al agregar cliente: " + e.getMessage();
        }
    }

    /**
     * Elimina un cliente del sistema a partir de su email.
     *
     * Primero busca el cliente correspondiente en el modelo y, si existe,
     * lo elimina de la lista. También se eliminan sus pedidos asociados.
     *
     * @param email correo electrónico del cliente que se desea eliminar
     * @return true si el cliente existía y se eliminó correctamente, false si no se encontró
     */
    public String deleteCliente(String email) {
        try {
            Cliente cliente = datos.buscarCliente(email);
            datos.eliminarCliente(cliente);
            return "Cliente con email " + cliente.getEmail() + " eliminado correctamente.";
        } catch (ElementoNoEncontradoException e) {
            return "No se encontró ningún cliente con email " + email;
        } catch (Exception e) {
            return "Error inesperado al eliminar cliente. " + e.getMessage();
        }
    }

    /**
     * Busca un cliente en el sistema a partir de su email.
     *
     * @param email correo electrónico del cliente a buscar
     * @return el objeto Cliente si se encuentra, o null si no existe
     */
    public Cliente buscarCliente(String email) {
        try {
            return datos.buscarCliente(email);
        } catch (ElementoNoEncontradoException e) {
            System.err.println("Aviso: " + e.getMessage());
            return null;
        }
    }

    /**
     * Devuelve una lista con todos los clientes registrados en el sistema.
     *
     * Este método simplemente llama al modelo para recuperar la lista de clientes.
     *
     * @return lista de clientes actuales
     */
    public List<Cliente> listarClientes() {
        try {
            return datos.listarClientes();
        } catch (Exception e) {
            System.err.println("Error al listar clientes. " + e.getMessage());
            return Collections.emptyList();
        }
    }
}