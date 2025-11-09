package ojoaldato.vista;

import ojoaldato.controlador.ClienteControlador;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.ClientePremium;

import java.util.List;
import java.util.Scanner;

public class ClientesVista {

    private final ClienteControlador repositorioCliente;
    private final Scanner entrada;

    public ClientesVista(ClienteControlador repositorioCliente, Scanner entrada){
        this.repositorioCliente = repositorioCliente;
        this.entrada = entrada;
    }

    // =======================
    // Menú Clientes
    // =======================
    public void menuClientes() {
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
