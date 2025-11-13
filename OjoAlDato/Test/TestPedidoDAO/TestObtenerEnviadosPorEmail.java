package TestPedidoDAO;

import ojoaldato.DAO.PedidoDAO;
import ojoaldato.DAO.PedidoDAOImpl;
import ojoaldato.modelo.Pedido;

import java.util.List;

public class TestObtenerEnviadosPorEmail {
    public static void main(String[] args) {
        PedidoDAO pedidoDAO = new PedidoDAOImpl();

        String emailCliente = "cliente1@ejemplo.com";
        List<Pedido> pedidos = pedidoDAO.obtenerEnviadosPorEmail(emailCliente);

        System.out.println("Pedidos enviados para el cliente: " + emailCliente);
        System.out.println("-------------------------------------------");

        if (pedidos.isEmpty()) {
            System.out.println("No se encontraron pedidos enviados para este cliente.");
        } else {
            for (Pedido p : pedidos) {
                System.out.println("Num. pedido:  " + p.getNumPedido());
                System.out.println("Artículo:     " + p.getArticulo().getCodigo());
                System.out.println("Cantidad:     " + p.getCantidad());
                System.out.println("Fecha/Hora:   " + p.getFechaHora());
                System.out.println("Importe total:" + p.calcularImporteTotal());
                System.out.println("-------------------------------------------");
            }
        }
    }
}

