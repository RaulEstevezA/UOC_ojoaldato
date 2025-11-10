package ojoaldato.vista;

import ojoaldato.controlador.ArticuloControlador;
import ojoaldato.modelo.Articulo;
import  java.math.BigDecimal;
import java.util.Scanner;
import java.util.List;

public class ArticulosVista {

    private final ArticuloControlador repositorioArticulo;
    private final Scanner entrada;

    public ArticulosVista(ArticuloControlador repositorioArticulo, Scanner entrada) {
        this.repositorioArticulo = repositorioArticulo;
        this.entrada = entrada;
    }

    // =======================
    // Menú Artículos
    // =======================
    public void menuArticulos() {
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
                    Articulo a = pedirDatosArticulo();
                    System.out.println(repositorioArticulo.addArticulo(a));
                }
                case 2 -> mostrarLista(repositorioArticulo.getAllArticulos(), null);
                case 0 -> System.out.println("Volviendo al menú principal.");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 0);
    }

    // =======================
    // Método auxiliar para pedir datos de Artículo
    // =======================
    private Articulo pedirDatosArticulo() {
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
        System.out.println("Stock actual:");
        int stock = Integer.parseInt(entrada.nextLine());

        Articulo a = new Articulo(codigo, descripcion, pvp, gastosEnvio, tiempoPreparacion, stock);
        repositorioArticulo.addArticulo(a);
        return a;
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
