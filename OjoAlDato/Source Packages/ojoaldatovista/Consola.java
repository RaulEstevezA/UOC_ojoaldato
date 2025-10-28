package ojoaldatovista;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.List;

import ojoaldato.controlador.ArticuloControlador;
import ojoaldato.modelo.Articulo;
import ojoaldato.controlador.ClienteControlador;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.ClientePremium;

// Clase para mostrar el menú principal del programa por consola

public class Consola {

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

                    Articulo a = new Articulo(codigo, descripcion, pvp, gastosEnvio, tiempoPreparacion);
                    System.out.println(articulo.addArticulo(a));
                }
                case 2 -> {
                    List<Articulo> listarArticulos = articulo.listarArticulos();
                }
                case 0 -> System.out.println("Volviendo al menú principal.");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion !=0);
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
                    System.out.println("Nombre y Apellidos: ");
                    String nombre = entrada.nextLine();
                    System.out.println("Domicilio: ");
                    String domicilio = entrada.nextLine();
                    System.out.println("NIF: ");
                    String nif = entrada.nextLine();
                    System.out.println("Email: ");
                    String email = entrada.nextLine();
                    Cliente c = new ClienteEstandar(nombre, domicilio, nif, email); //Constructor ClienteEstandar ?
                    System.out.println(cliente.addCliente(c));
                }
                case 2 -> {
                    System.out.println("Nombre y Apellidos: ");
                    String nombre = entrada.nextLine();
                    System.out.println("Domicilio: ");
                    String domicilio = entrada.nextLine();
                    System.out.println("NIF: ");
                    String nif = entrada.nextLine();
                    System.out.println("Email: ");
                    String email = entrada.nextLine();
                    System.out.println("Cuota: ");
                    BigDecimal cuota = new BigDecimal(entrada.nextLine());
                    Cliente c = new ClientePremium(nombre, domicilio, nif, email, cuota, null);
                    System.out.println(cliente.addCliente(c));
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
}