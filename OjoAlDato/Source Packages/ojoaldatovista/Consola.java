package ojoaldatovista;
import java.util.Scanner;

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
        int opcion;
        do {
            System.out.println("\n---GESTIÓN DE ARTÍCULOS---");
            System.out.println("1. Añadir Artículo");
            System.out.println("2. Mostrar Artículos");
            System.out.println("0. Volver al menú");
            System.out.println("Por favor, seleccione una opción:");
            opcion = Integer.parseInt(entrada.nextLine());

            switch (opcion) {  // Pendiente de implementar
                case 1 -> {
                }
                case 2 -> {
                }
                case 0 -> System.out.println("Volviendo al menú principal.");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion !=0);
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