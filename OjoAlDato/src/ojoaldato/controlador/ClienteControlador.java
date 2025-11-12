package ojoaldato.controlador;

import ojoaldato.excepcion.ElementoDuplicadoException;
import ojoaldato.excepcion.ElementoNoEncontradoException;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.Datos;

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
    private Datos datos;


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
     * @return Mensaje con el resultado de la operación
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
     * Primero busca el cliente correspondiente en el ojoaldato.modelo y, si existe,
     * lo elimina de la lista. También se eliminan sus pedidos asociados.
     *
     * @param email correo electrónico del cliente que se desea eliminar
     * @return Mensaje con el resultado de la operación
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
     * Este método simplemente llama al ojoaldato.modelo para recuperar la lista de clientes.
     *
     * @return lista de clientes actuales o lista vacía
     */
    public List<Cliente> listarClientes() {
        try {
            return datos.listarClientes();
        } catch (Exception e) {
            System.err.println("Error al listar clientes. " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Devuelve una lista con todos los clientes de tipo estándar registrados en el sistema.
     *
     * @return Lista de clientes estándar. Si no hay clientes de este tipo, devuelve una lista vacía.
     */
    public List<Cliente> listarClientesEstandar() {
        try {
            return datos.listarClientesEstandar();
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Aviso: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Devuelve una lista con todos los clientes de tipo premium registrados en el sistema.
     *
     * @return Lista de clientes premium. Si no hay clientes de este tipo, devuelve una lista vacía.
     */
    public List<Cliente> listarClientesPremium() {
        try {
            return datos.listarClientesPremium();
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Aviso: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}