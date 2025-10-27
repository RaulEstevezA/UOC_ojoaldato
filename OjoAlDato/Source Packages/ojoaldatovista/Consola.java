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

    // SUBMENÚ ARTÍCULOS

    private static void menuArticulos(Scanner entrada) {
        int opcion;
        do {

        } while (opcion !=0);
    }

    // SUBMENÚ CLIENTES

    private static void menuClientes(Scanner entrada) {
        int opcion;
        do {

        } while (opcion !=0);
    }

    // SUBMENÚ PEDIDOS

    private static void menuPedidos(Scanner entrada) {
        int opcion;
        do {

        } while (opcion !=0);
    }
}
