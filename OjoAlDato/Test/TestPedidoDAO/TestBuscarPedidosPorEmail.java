package TestPedidoDAO;

import ojoaldato.DAO.PedidoDAO;
import ojoaldato.DAO.PedidoDAOImpl;
import ojoaldato.modelo.Pedido;

import java.util.List;

public class TestBuscarPedidosPorEmail {
    public static void main(String[] args) {
        PedidoDAO pedidoDAO = new PedidoDAOImpl();
        String email = "cliente1@ejemplo.com";

        List<Pedido> pedidos = pedidoDAO.buscarPorEmail(email);

        if (pedidos.isEmpty()) {
            System.out.println("No se encontraron pedidos para el cliente con email: " + email);
            return;
        }

        String nombreCliente = pedidos.get(0).getCliente().getNombre();
        System.out.println("Pedidos del cliente: " + nombreCliente + " (Email: " + email + ")");
        System.out.println("--------------------------------------------------");

        for (Pedido p : pedidos) {
            System.out.println("Número de pedido: " + p.getNumPedido());
            System.out.println("Cliente:         " + p.getCliente().getNombre());
            System.out.println("Artículo:        " + p.getArticulo().getDescripcion());
            System.out.println("Cantidad:        " + p.getCantidad());
            System.out.println("Fecha/Hora:      " + p.getFechaHora());
            System.out.println("Importe total:   " + p.calcularImporteTotal());
            System.out.println("--------------------------------------------------");
        }
    }
}