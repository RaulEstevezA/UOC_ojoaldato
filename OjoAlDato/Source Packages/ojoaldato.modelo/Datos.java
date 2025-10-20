package ojoaldato.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase {@code Datos} que actúa como la capa de gestión de datos dentro del patrón MVC.
 *
 * Esta clase se encarga de almacenar y gestionar la información relativa a los clientes, artículos y pedidos
 * del sistema. Utiliza colecciones dinámicas de Java para facilitar la inserción, eliminación y búsqueda de elementos.
 *
 * El almacenamiento de pedidos se realiza mediante un {@code Map} que asocia cada cliente con su lista de pedidos
 * correspondiente.
 *
 * @version 1.0
 */
public class Datos {
    /** Lista de clientes registrados en el sistema*/
    private List<Cliente> clientes;

    /** Lista de artículos disponibles en el sistema*/
    private List<Articulo> articulos;

    /** Mapa que relaciona cada cliente con su lista de pedidos*/
    private Map<Cliente, List<Pedido>> pedidosPorCliente;

    /** Constructor que inicializa las colecciones utilizadas en la clase.*/
    public Datos() {
        clientes = new ArrayList<>();
        articulos = new ArrayList<>();
        pedidosPorCliente = new HashMap<>();
    }
    // ================================
    // CLIENTES
    // ================================

    /**
     * Agrega un nuevo cliente a la lista cliente.
     *
     * @param c Cliente a agregar
     * @throws RuntimeException si el cliente ya existe en la lista
     */
    public void agregarCliente(Cliente c) throws RuntimeException { // Añadir excepcion personalizada
        if (clientes.contains(c)) {
            System.out.println("El cliente ya existe"); // Cambiar Sysout por excepcion
        }
        clientes.add(c);
    }

    /**
     * Elimina un cliente del sistema y sus pedidos asociados
     *
     * @param c Cliente a eliminar
     * @throws RuntimeException si el cliente no existe
     */
    public void eliminarCliente(Cliente c) throws RuntimeException {
        if (!clientes.remove(c)) {
            System.out.println("El cliente no existe"); // Cambiar por excepcion
        }
        pedidosPorCliente.remove(c);
    }

    /**
     * Busca un cliente por su dirección de correo electrónico
     *
     * @param email correo electrónico del cliente a buscar
     * @return El cliente encontrado, o {@code null} si no existe
     */
    public Cliente buscarCliente(String email) {
        for (Cliente c : clientes) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Devuelve una lista de todos los clientes registrados
     *
     * @return Lista de clientes
     */
    public List<Cliente> listarClientes() {
        return clientes;
    }
}
