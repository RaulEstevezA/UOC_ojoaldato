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

    // ================================
    // ARTÍCULOS
    // ================================

    /**
     * Agrega un artículo al sistema
     *
     * @param a Artículo a agregar
     * @throws RuntimeException si el artículo ya existe
     */
    public void agregarArticulo(Articulo a) throws RuntimeException { // Crear excepción personalizada
        if (articulos.contains(a)) {
            System.out.println("El artículo ya existe"); // Cambiar por excepción
        }
        articulos.add(a);
    }

    /**
     * Elimina un artículo del sistema.
     *
     * @param a Artículo a eliminar
     * @throws RuntimeException si el artículo no existe
     */
    public void eliminarArticulo(Articulo a) throws RuntimeException {
        if (!articulos.remove(a)) {
            System.out.println("El artículo no existe");
        }
    }

    /**
     * Busca un artículo por su código
     *
     * @param codigo código identificador del artículo
     * @return El artículo encontrado, o {@code null} si no existe
     */
    public Articulo buscarArticulo(String codigo) {
        for (Articulo a : articulos) {
            if(a.getCodigo().equalsIgnoreCase(codigo)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Devuelve una lista de todos los artículos disponibles.
     *
     * @return Lista de artículos
     */
    public List<Articulo> listarArticulo() {
        return new ArrayList<>(articulos);
    }

    // ================================
    // PEDIDOS
    // ================================

    /**
     * Crea un nuevo pedido asociado a un cliente.
     *
     * @param c Cliente que realiza el pedido
     * @param p Pedido a registrar
     */
    public void crearPedido(Cliente c, Pedido p) {
        pedidosPorCliente.computeIfAbsent(c, k -> new ArrayList<>()).add(p);
    }

    /**
     * Devuelve una lista de pedidos realizados por un cliente específico
     *
     * @param c Cliente cuyos pedidos se desean consultar
     * @return Lista de pedidos del cliente, o una lista vacía si no tiene pedidos.
     */
    public List<Pedido> listarPedidosPorCliente(Cliente c) {
        return pedidosPorCliente.getOrDefault(c, new ArrayList<>());
    }

    /**
     * Devuelve el mapa completo de clientes y sus pedidos.
     *
     * @return Mapa de pedidos por cliente.
     */
    public Map<Cliente, List<Pedido>> getPedidosPorCliente() {
        return pedidosPorCliente;
    }

    /**
     * Devuelve una lista con todos los pedidos registrados en el sistema.
     *
     * @return Lista de todos los pedidos.
     */
    public List<Pedido> listarTodosPedidos() {
        List<Pedido> todos = new ArrayList<>();
        for (List<Pedido> pedidos : pedidosPorCliente.values()) {
            todos.addAll(pedidos);
        }
        return todos;
    }
}
