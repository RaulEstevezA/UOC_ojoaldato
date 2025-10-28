package ojoaldato.modelo;

import ojoaldato.exception.ElementoNoEncontradoException;
import ojoaldato.exception.PedidoInvalidoException;
import ojoaldato.exception.ElementoDuplicadoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Clase {@code Datos} que actúa como la capa de gestión de datos dentro del patrón MVC.
 *
 * Esta clase se encarga de almacenar y gestionar la información relativa a los clientes, artículos y pedidos
 * del sistema. Utiliza colecciones dinámicas de Java para facilitar la inserción, eliminación y búsqueda de elementos.
 *
 * El almacenamiento de pedidos se realiza mediante un {@code Map} que asocia cada cliente con su lista de pedidos
 * correspondiente.
 *
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
     * @throws ElementoDuplicadoException si el cliente ya existe en la lista
     */
    public void agregarCliente(Cliente c) {
        if (clientes.contains(c)) {
            throw new ElementoDuplicadoException("El cliente con email " + c.getEmail() + " ya está registrado.");
        }
        clientes.add(c);
    }

    /**
     * Elimina un cliente del sistema y sus pedidos asociados
     *
     * @param c Cliente a eliminar
     * @throws ElementoNoEncontradoException si el cliente no existe
     */
    public void eliminarCliente(Cliente c) {
        if (!clientes.remove(c)) {
            throw new ElementoNoEncontradoException("El cliente con email " + c.getEmail() + " no existe.");
        }
        clientes.remove(c);
        pedidosPorCliente.remove(c);
    }

    /**
     * Busca un cliente por su dirección de correo electrónico
     *
     * @param email correo electrónico del cliente a buscar
     * @return El cliente encontrado
     * @throws ElementoNoEncontradoException si no existe
     */
    public Cliente buscarCliente(String email) {
        return buscarElemento(clientes, c -> c.getEmail().equalsIgnoreCase(email),
                "No se encontró ningún cliente con el email: " + email);
    }

    /**
     * Devuelve una lista de todos los clientes registrados
     *
     * @return Lista de clientes
     */
    public List<Cliente> listarClientes() {
        return clientes;
    }

    public List<Cliente> listarClientesEstandar() {
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente c : clientes) {
            if (c instanceof ClienteEstandar) {
                resultado.add(c);
            }
        }

        if (resultado.isEmpty()) {
            throw new ElementoNoEncontradoException("No se encontraron clientes estándar registrados.");
        }

        return resultado;
    }

    public List<Cliente> listarClientesPremium() {
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente c : clientes) {
            if (c instanceof ClientePremium) {
                resultado.add(c);
            }
        }

        if (resultado.isEmpty()) {
            throw new ElementoNoEncontradoException("No se encontraron clientes premium registrados.");
        }

        return resultado;
    }

    // ================================
    // ARTÍCULOS
    // ================================

    /**
     * Agrega un artículo al sistema
     *
     * @param a Artículo a agregar
     * @throws ElementoDuplicadoException si el artículo ya existe
     */
    public void agregarArticulo(Articulo a) {
        if (articulos.contains(a)) {
            throw new ElementoDuplicadoException("El artículo con código " + a.getCodigo() + " ya existe");
        }
        articulos.add(a);
    }

    /**
     * Elimina un artículo del sistema.
     *
     * @param a Artículo a eliminar
     * @throws ElementoNoEncontradoException si el artículo no existe
     */
    public void eliminarArticulo(Articulo a) {
        if (!articulos.remove(a)) {;
            throw new ElementoNoEncontradoException("No se encontró el artículo con código " + a.getCodigo());
        }
    }

    /**
     * Busca un artículo por su código
     *
     * @param codigo código identificador del artículo
     * @return El artículo encontrado
     * @throws ElementoNoEncontradoException si no existe
     */
    public Articulo buscarArticulo(String codigo) {
        return buscarElemento(articulos, a -> a.getCodigo().equalsIgnoreCase(codigo),
                "No se encontró ningún artículo con el código: " + codigo);
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
        if (c == null) {
            throw new PedidoInvalidoException("El cliente no puede ser nulo al crear el pedido.");
        }

        if (!clientes.contains(c)) {
            throw new PedidoInvalidoException("El cliente con email " + c.getEmail() + " no está registrado en el sistema.");
        }

        if (p == null) {
            throw new PedidoInvalidoException("El pedido no puede ser nulo.");
        }

        List<Pedido> pedidosClientes = pedidosPorCliente.computeIfAbsent(c, k -> new ArrayList<>());
        if (pedidosClientes.contains(p)) {
            throw new PedidoInvalidoException("El pedido ya existe para este cliente.");
        }

        pedidosClientes.add(p);
    }

    /**
     * Devuelve una lista de pedidos realizados por un cliente específico
     *
     * @param c Cliente cuyos pedidos se desean consultar
     * @return Lista de pedidos del cliente, o una lista vacía si no tiene pedidos.
     */
    public List<Pedido> listarPedidosPorCliente(Cliente c) {
        if (c == null) {
            throw new PedidoInvalidoException("El cliente no puede ser nulo.");
        }

        if (!clientes.contains(c)) {
            throw new ElementoNoEncontradoException("El cliente con email " + c.getEmail() + " no está registrado.");
        }

        return pedidosPorCliente.getOrDefault(c, new ArrayList<>());
    }

    /**
     * Devuelve el mapa completo de clientes y sus pedidos.
     *
     * @return Mapa de pedidos por cliente.
     */
    public Map<Cliente, List<Pedido>> getPedidosPorClientes() {
        if (pedidosPorCliente.isEmpty()) {
            throw new ElementoNoEncontradoException("No se encontraron pedidos registrados en el sistema.");
        }

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

        if (todos.isEmpty()) {
            throw new ElementoNoEncontradoException("No se encontraron pedidos registrados en el sistema.");
        }

        return todos;
    }


    // ================================
    // PEDIDOS POR TIPO CLIENTE
    // ================================

    /**
     * Devuelve una lista con todos los pedidos realizados por clientes estandar.
     *
     * @return Lista de pedidos de clientes estandar
     */
    public List<Pedido> listarPedidosClientesEstandar() {
        List<Pedido> resultado = new ArrayList<>();
        for (Map.Entry<Cliente, List<Pedido>> entry : pedidosPorCliente.entrySet()) {
            if (entry.getKey() instanceof ClienteEstandar) {
                resultado.addAll(entry.getValue());
            }
        }

        if (resultado.isEmpty()) {
            throw new ElementoNoEncontradoException("No se encontraron pedidos de clientes estándar.");

        }

        return resultado;
    }

    /**
     * Devuelve una lista con todos los pedidos realizados por clientes premium.
     *
     * @return Lista de pedidos de clientes premium
     */
    public List<Pedido> listarPedidosClientesPremium() {
        List<Pedido> resultado = new ArrayList<>();
        for (Map.Entry<Cliente, List<Pedido>> entry : pedidosPorCliente.entrySet()) {
            if (entry.getKey() instanceof ClientePremium) {
                resultado.addAll(entry.getValue());
            }
        }
        if (resultado.isEmpty()) {
            throw new ElementoNoEncontradoException("No se encontraron pedidos de clientes premium.");

        }

        return resultado;
    }

    // ==================================
    // MÉTODOS GENÉRICOS (JAVA GENERICS)
    // ==================================

    /**
     * Método genérico para buscar un único elemento dentro de una lista según una condición
     *
     * @param <T> Tipo de elementos de la lista
     * @param lista Lista donde se realizará la búsqueda
     * @param condicion Expresión lambda que define la condición de búsqueda
     * @return El primer elemento que cumple la condición, o null si no se encuentra
     */
    private <T> T buscarElemento(List<T> lista, Predicate<T> condicion, String mensajeError) {
        for (T elemento : lista) {
            if (condicion.test(elemento)) {
                return elemento;
            }
        }

        throw new ElementoNoEncontradoException(mensajeError);

    }

    /**
     * Método genérico para filtrar todos los elementos de una lista que cumpla una condición
     *
     * @param <T> Tipo de elementos de la lista
     * @param lista Lista donde se realizará la búsqueda
     * @param condicion Expresión lambda que define la condición de filtrado
     * @return Lista con todos los elementos que cumplen la condición
     */
    private <T> List<T> filtrarElementos(List<T> lista, Predicate<T> condicion) {
        List<T> resultado = new ArrayList<>();
        for (T elemento : lista) {
            if (condicion.test(elemento)) {
                resultado.add(elemento);
            }
        }

        return resultado;
    }
}
