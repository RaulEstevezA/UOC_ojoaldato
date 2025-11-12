package ojoaldato.vista;

import ojoaldato.controlador.ArticuloControlador;
import ojoaldato.controlador.ClienteControlador;
import ojoaldato.controlador.PedidoControlador;
import ojoaldato.modelo.Articulo;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.Pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class PedidosVista {
    private final PedidoControlador repositorioPedidos;
    private final ClienteControlador repositorioCliente;
    private final ArticuloControlador repositorioArticulo;
    private final Scanner entrada;

    public PedidosVista(PedidoControlador repositorioPedidos, ClienteControlador repositorioCliente, ArticuloControlador repositorioArticulo, Scanner entrada) {
        this. repositorioArticulo = repositorioArticulo;
        this.repositorioPedidos = repositorioPedidos;
        this.repositorioCliente = repositorioCliente;
        this.entrada = entrada;
    }

    // =======================
    // Menú Pedidos
    // =======================
    public void menuPedidos() {
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
                    Pedido p = pedirDatosPedido();
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
                    List<Pedido> pendientes = repositorioPedidos.listarPedidosPendientes(email);
                    System.out.println("\n---- PEDIDOS PENDIENTES" + (email != null ? " DE " + email : "") + " ----");
                    mostrarLista(pendientes, "No hay pedidos pendientes" + (email != null ? " para el email " + email : "") + ".");
                }
                case 4 -> {
                    System.out.println("Filtrar por email (Enter para todos): ");
                    String email = entrada.nextLine();
                    if (email.isEmpty()) email = null;
                    List<Pedido> enviados = repositorioPedidos.listarPedidosEnviados(email);
                    System.out.println("\n---- PEDIDOS ENVIADOS" + (email != null ? " DE " + email : "") + " ----");
                    mostrarLista(enviados, "No hay pedidos enviados" + (email != null ? " para el email " + email : "") + ".");
                }
                case 0 -> System.out.println("Volviendo al menú principal.");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 0);
    }

    // =======================
    // Método auxiliar para pedir datos de Pedido
    // =======================
    private Pedido pedirDatosPedido() {
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
        Articulo articulo = repositorioArticulo.getArticuloByCodigo(codigo);
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
