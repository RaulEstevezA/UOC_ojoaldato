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
}
