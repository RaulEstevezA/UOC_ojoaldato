package TestPedidoDAO;

import ojoaldato.DAO.PedidoDAO;
import ojoaldato.DAO.PedidoDAOImpl;
import ojoaldato.modelo.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestCrearPedido {
    public static void main(String[] args) {
        try {
            PedidoDAO pedidoDAO = new PedidoDAOImpl();

            Cliente cliente = new ClienteEstandar("Cliente Premium", "Avenida Real 456", "87654321B", "cliente2@ejemplo.com");
            Articulo articulo = new Articulo("ART002", "Artículo de prueba 2", BigDecimal.valueOf(50.0), BigDecimal.valueOf(5.0), 24, 10);

            Pedido pedido = new Pedido(3, cliente, articulo, 2, LocalDateTime.now());

            pedidoDAO.crear(pedido);

            System.out.println("Pedido creado correctamente:");
            System.out.println("Número de pedido: " + pedido.getNumPedido());
            System.out.println("Cliente: " + pedido.getCliente().getNombre());
            System.out.println("Email cliente: " + pedido.getCliente().getEmail());
            System.out.println("Artículo: " + pedido.getArticulo().getDescripcion());
            System.out.println("Cantidad: " + pedido.getCantidad());
            System.out.println("Fecha y hora: " + pedido.getFechaHora());
            System.out.println("Precio total: " + pedido.calcularImporteTotal());
            System.out.println("Gastos de envío: " + pedido.getArticulo().getGastosEnvio());

        } catch (Exception e) {
            System.err.println("Error en el test: " + e.getMessage());
        }
    }
}