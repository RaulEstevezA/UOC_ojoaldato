package ojoaldato.vista;

import ojoaldato.controlador.ArticuloControlador;
import ojoaldato.controlador.ClienteControlador;
import ojoaldato.controlador.PedidoControlador;
import ojoaldato.modelo.Articulo;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.ClientePremium;
import ojoaldato.modelo.Datos;

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
            System.out.println("4. Cargar datos de ejemplo");
            System.out.println("0. Salir");
            System.out.println("Por favor, seleccione una opción:");
            opcion = Integer.parseInt(entrada.nextLine());

            switch (opcion) {
                case 1 -> articulosVista.menuArticulos();
                case 2 -> clientesVista.menuClientes();
                case 3 -> pedidosVista.menuPedidos();
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
}