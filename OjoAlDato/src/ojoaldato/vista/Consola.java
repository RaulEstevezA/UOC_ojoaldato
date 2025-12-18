package ojoaldato.vista;

import ojoaldato.controlador.ArticuloControlador;
import ojoaldato.controlador.ClienteControlador;
import ojoaldato.controlador.PedidoControlador;

import java.util.Scanner;

/**
 *
 */

public class Consola {
    // Inicialización de los controladores para acceder a sus métodos
    private final static ArticuloControlador repositorioArticulo = new ArticuloControlador();
    private final static ClienteControlador repositorioCliente = new ClienteControlador();
    private final static PedidoControlador repositorioPedidos = new PedidoControlador();

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in); //Scanner para leer los datos del usuario
        ArticulosVista articulosVista = new ArticulosVista(repositorioArticulo, entrada);
        ClientesVista clientesVista = new ClientesVista(repositorioCliente, entrada);
        PedidosVista pedidosVista = new PedidosVista(repositorioPedidos, repositorioCliente, repositorioArticulo, entrada);
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
                case 1 -> articulosVista.menuArticulos();
                case 2 -> clientesVista.menuClientes();
                case 3 -> pedidosVista.menuPedidos();
                case 0 -> System.out.println("Cerrando el programa...");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 0);
        entrada.close();
    }
}