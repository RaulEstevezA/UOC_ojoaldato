package ojoaldatovista;

import ojoaldato.controlador.ArticuloControlador;
import ojoaldato.controlador.ClienteControlador;
import ojoaldato.controlador.PedidoControlador;
import ojoaldato.modelo.Articulo;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.ClientePremium;
import ojoaldato.modelo.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

// Clase para mostrar el menú principal del programa por consola

public class Consola {
    // Inicialización de los controladores para acceder a sus métodos
    private static ClienteControlador repositorioCliente = new ClienteControlador();
    private static ArticuloControlador repositorioArticulo = new ArticuloControlador();
    private static PedidoControlador pedidosControlador = new PedidoControlador();

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in); //Scanner para leer los datos del usuario
        int opcion;

        // MENÚ PRINCIPAL
        do {
            System.out.println("---MENÚ PRINCIPAL---");
            System.out.println("1. Gestión de Artículos");
            System.out.println("2. Gestión de Clientes");
            System.out.println("3. Gestión de Pedidos");
            System.out.println("0. Salir");
            System.out.println("Por favor, seleccione una opción:");
            opcion = Integer.parseInt(entrada.nextLine());

            switch (opcion) {
                case 1 -> menuArticulos(entrada);
                case 2 -> menuClientes(entrada);
                case 3 -> menuPedidos(entrada);
                case 0 -> System.out.println("Cerrando el programa...");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 0);
        entrada.close();
    }


    // --- SUB MENÚS --- //

    // SUBMENÚ ARTÍCULOS
    private static void menuArticulos(Scanner entrada) {
        ArticuloControlador articulo = new ArticuloControlador();
        int opcion;
        do {
            System.out.println("\n---GESTIÓN DE ARTÍCULOS---");
            System.out.println("1. Añadir Artículo");
            System.out.println("2. Mostrar Artículos");
            System.out.println("0. Volver al menú");
            System.out.println("Por favor, seleccione una opción:");
            opcion = Integer.parseInt(entrada.nextLine());

            switch (opcion) {
                case 1 -> {
                }
                case 2 -> {
                    List<Articulo> listarArticulos = articulo.listarArticulos();
                }
                case 0 -> System.out.println("Volviendo al menú principal.");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion !=0);
    }

    private static Articulo pedirDatosArticulo(Scanner entrada) {
        System.out.println("Código del artículo: ");
        String codigo = entrada.nextLine();
        System.out.println("Descripción del artículo: ");
        String descripcion = entrada.nextLine();
        System.out.println("PVP: ");
        BigDecimal pvp = new BigDecimal(entrada.nextLine());
        System.out.println("Gastos de envío: ");
        BigDecimal gastosEnvio = new BigDecimal(entrada.nextLine());
        System.out.println("Tiempo de preparación: ");
        int tiempoPreparacion = Integer.parseInt(entrada.nextLine());

        return new Articulo(codigo, descripcion, pvp, gastosEnvio, tiempoPreparacion);
    }

    // SUBMENÚ CLIENTES
    private static void menuClientes(Scanner entrada) {
        ClienteControlador cliente = new ClienteControlador();
        int opcion;
        do {
            System.out.println("\n---GESTIÓN DE CLIENTES---");
            System.out.println("1. Añadir Cliente");
            System.out.println("2. Mostrar Clientes");
            System.out.println("3. Mostrar Clientes Estándar");
            System.out.println("4. Mostrar Clientes Premium");
            System.out.println("0. Volver al menú");
            System.out.println("Por favor, seleccione una opción:");
            opcion = Integer.parseInt(entrada.nextLine());

            switch (opcion) {
                case 1 -> {
                    Cliente c = pedirDatosCliente(entrada, false);
                    System.out.println(repositorioCliente.addCliente(c));
                }
                case 2 -> {
                    Cliente c = pedirDatosCliente(entrada, true);
                    System.out.println(repositorioCliente.addCliente(c));
                }
                case 3 -> {
                    List<Cliente> listarClientesEstandar = cliente.listarClientesEstandar();
                }
                case 4 -> {
                    List<Cliente> listarClientesPremium = cliente.listarClientesPremium();
                }
                case 0 -> System.out.println("Volviendo al menú principal.");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion !=0);
    }

    private static Cliente pedirDatosCliente(Scanner entrada, boolean esPremium) {
        System.out.println("Nombre y Apellidos: ");
        String nombre = entrada.nextLine();
        System.out.println("Domicilio: ");
        String domicilio = entrada.nextLine();
        System.out.println("NIF: ");
        String nif = entrada.nextLine();
        System.out.println("Email: ");
        String email = entrada.nextLine();

        if (esPremium) {
            Cliente cp = new ClientePremium(nombre, domicilio, nif, email);
            return cp;
        } else {
            return new ClienteEstandar(nombre, domicilio, nif, email);
        }
    }

    // SUBMENÚ PEDIDOS
    private static void menuPedidos(Scanner entrada) {
        int opcion;
        do {
            System.out.println("\n---GESTIÓN DE PEDIDOS---");
            System.out.println("1. Añadir Pedido");
            System.out.println("2. Eliminar Pedido");
            System.out.println("3. Mostrar Pedidos pendientes");
            System.out.println("4. Mostrar Pedidos enviados");
            System.out.println("0. Volver al menú");
            System.out.println("Por favor, seleccione una opción:");
            opcion = Integer.parseInt(entrada.nextLine());

            switch (opcion) { // Pendiente de implementar
                case 1 -> {
                    Pedido p = pedirDatosPedido(entrada);
                    if (p != null) {
                        Cliente c = p.getCliente();
                        System.out.println(pedidosControlador.addPedido(c, p));
                    }
                }
                case 2 -> {

                }
                case 3 -> {
                }
                case 4 -> {
                }
                case 0 -> System.out.println("Volviendo al menú principal.");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion !=0);
    }

    private static Pedido pedirDatosPedido(Scanner entrada) {
        System.out.println("Número del pedido: ");
        int numPedido = Integer.parseInt(entrada.nextLine());

        // ---- Seleccionar cliente ----
        System.out.println("Email del cliente: ");
        String email = entrada.nextLine();
        Cliente cliente = repositorioCliente.buscarCliente(email);
        if (cliente == null) {
            System.out.println("No se encontró un cliente con el email proporcionado.");
            return null;
        }

        // ---- Seleccionar artículo -----
        System.out.println("Código del artículo: ");
        String codigo = entrada.nextLine();
        Articulo articulo = repositorioArticulo.buscarArticulo(codigo);
        if (articulo == null) {
            System.out.println("No se encontró ningún artículo con el código proporcionado.");
            return null;
        }

        // ---- Cantidad ----
        System.out.println("Cantidad: ");
        int cantidad = Integer.parseInt(entrada.nextLine());

        // ---- Timestamp
        LocalDateTime fechaHora = LocalDateTime.now();

        return new Pedido(numPedido, cliente, articulo, cantidad, fechaHora);
    }

}