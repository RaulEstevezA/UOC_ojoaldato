package ojoaldatovista;

import ojoaldato.controlador.ArticuloControlador;
import ojoaldato.controlador.ClienteControlador;
import ojoaldato.controlador.PedidoControlador;
import ojoaldato.modelo.Articulo;
import ojoaldato.modelo.CargarDatos;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.ClientePremium;
import ojoaldato.modelo.Datos;
import ojoaldato.modelo.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 *
 */

public class Consola {
    // Inicialización de los controladores para acceder a sus métodos
    // Ahora los controladores usan el mismo objeto "datos" compartido para evitar problemas con la información
    private static Datos datos = new Datos();
    private static ClienteControlador repositorioCliente = new ClienteControlador(datos);
    private static ArticuloControlador repositorioArticulo = new ArticuloControlador(datos);
    private static PedidoControlador repositorioPedidos = new PedidoControlador(datos);

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in); //Scanner para leer los datos del usuario
        int opcion;

        // MENÚ PRINCIPAL
        do {
            System.out.println("---MENÚ PRINCIPAL---");
            System.out.println("1. Gestión de Artículos");
            System.out.println("2. Gestión de Clientes");
            System.out.println("3. Gestión de Pedidos");
            System.out.println("4. Cargar datos de ejemplo");
            System.out.println("0. Salir");
            System.out.println("Por favor, seleccione una opción:");
            opcion = Integer.parseInt(entrada.nextLine());

            switch (opcion) {
                case 1 -> menuArticulos(entrada);
                case 2 -> menuClientes(entrada);
                case 3 -> menuPedidos(entrada);
                case 4 -> {
                    CargarDatos.CargarDatos(repositorioCliente, repositorioArticulo,
                            repositorioPedidos);
                    System.out.println("Datos de ejemplo cargados correctamente.");
                }
                case 0 -> System.out.println("Cerrando el programa...");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 0);
        entrada.close();
    }


    // --- SUB MENÚS --- //

    // SUBMENÚ ARTÍCULOS
    private static void menuArticulos(Scanner entrada) {
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
                    Articulo a = pedirDatosArticulo(entrada);
                    System.out.println(repositorioArticulo.addArticulo(a));
                }
                case 2 -> mostrarLista(repositorioArticulo.listarArticulos(), null);
                case 0 -> System.out.println("Volviendo al menú principal.");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion !=0);
    }

    // =======================
    // Método auxiliar para pedir datos de Artículo
    // =======================
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
                    System.out.println("Tipo de cliente: (E para estándar / P para premium): ");
                    String tipo = entrada.nextLine();
                    boolean esPremium = tipo.equalsIgnoreCase("P");

                    Cliente c = pedirDatosCliente(entrada, esPremium);
                    System.out.println(repositorioCliente.addCliente(c));
                }
                case 2 -> mostrarLista(repositorioCliente.listarClientes(), null);
                case 3 -> mostrarLista(repositorioCliente.listarClientesEstandar(), null);
                case 4 -> mostrarLista(repositorioCliente.listarClientesPremium(), null);
                case 0 -> System.out.println("Volviendo al menú principal.");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion !=0);
    }

    // =======================
    // Método auxiliar para pedir datos de Cliente
    // =======================
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

            switch (opcion) {
                case 1 -> {
                    Pedido p = pedirDatosPedido(entrada);
                    if (p != null) {
                        Cliente c = p.getCliente();
                        System.out.println(repositorioPedidos.addPedido(c, p));
                    }
                }
                case 2 -> {
                    System.out.println("Número de pedido a eliminar: ");
                    int numPedido = Integer.parseInt(entrada.nextLine());
                    System.out.println(repositorioPedidos.deletePedido(numPedido));
                }
                case 3 -> {
                    System.out.println("Filtrar por email (Enter para todos): ");
                    String email = entrada.nextLine();
                    if (email.isEmpty()) email = null;
                    List<Pedido> pendientes = repositorioPedidos.listarPedidosPendientes();
                    System.out.println("\n---- PEDIDOS PENDIENTES ----");
                    mostrarLista(pendientes, "No hay pedidos pendientes.");
                }
                case 4 -> {
                    System.out.println("Filtrar por email (Enter para todos): ");
                    String email = entrada.nextLine();
                    if (email.isEmpty()) email = null;
                    List<Pedido> enviados = repositorioPedidos.listarPedidosEnviados();
                    System.out.println("\n---- PEDIDOS ENVIADOS ----");
                    mostrarLista(enviados, "No hay pedidos enviados.");
                }
                case 0 -> System.out.println("Volviendo al menú principal.");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion !=0);
    }

    // =======================
    // Método auxiliar para pedir datos de Pedido
    // =======================
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

    // =======================
    // Método auxiliar para mostrar listas
    // =======================

    private static <T> void mostrarLista(List<T> lista, String emptyMessage) {
        if (lista == null || lista.isEmpty()) {
            if (emptyMessage != null && !emptyMessage.isEmpty()) {
                System.out.print(emptyMessage);
            }
        }
        else lista.forEach(System.out::println);
    }

}