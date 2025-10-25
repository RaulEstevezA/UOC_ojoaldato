package ojoaldato.controlador;

import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.Datos;
import ojoaldato.modelo.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


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
    // Lista donde se almacenan los clientes en memoria

    // Constructor: el controlador necesita el modelo para trabajar
    public ClienteControlador(Datos datos) {
        this.datos = datos;
    }

    // ==============================
    // Métodos de gestión de clientes
    // ==============================

    /**
     * Agrega un nuevo cliente al sistema si no existe otro con el mismo email.
     *
     * El email se utiliza como identificador único del cliente, ya que no puede haber
     * dos clientes con el mismo correo electrónico.
     *
     * @param cliente Cliente que se desea agregar
     * @return true si se agregó correctamente, false si ya existía un cliente con ese email
     */
    public boolean agregarCliente(Cliente cliente) {
        // Busca en el modelo si ya existe un cliente con el mismo email
        if (datos.buscarCliente(cliente.getEmail()) == null) {
            // Si no existe, lo agrega a la lista de clientes del modelo
            datos.agregarCliente(cliente);
            return true; // Operación exitosa
        } else {
            // Si ya existe un cliente con ese email, no lo agrega
            return false;
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
    public boolean eliminarCliente(String email) {
        // Busca el cliente en el modelo por su email
        Cliente cliente = datos.buscarCliente(email);
        if (cliente != null) {
            // Si se encuentra, se elimina
            datos.eliminarCliente(cliente);
            return true;
        } else {
            // Si no existe ningún cliente con ese email, devuelve false
            return false;
        }
    }

    /**
     * Busca un cliente en el sistema a partir de su email.
     *
     * @param email correo electrónico del cliente a buscar
     * @return el objeto Cliente si se encuentra, o null si no existe
     */
    public Cliente buscarCliente(String email) {
        return datos.buscarCliente(email);
    }

    /**
     * Devuelve una lista con todos los clientes registrados en el sistema.
     *
     * Este método simplemente llama al modelo para recuperar la lista de clientes.
     *
     * @return lista de clientes actuales
     */
    public List<Cliente> listarClientes() {
        return datos.listarClientes();
    }
}