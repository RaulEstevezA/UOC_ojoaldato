package TestPedidoDAO;

import ojoaldato.DAO.PedidoDAO;
import ojoaldato.DAO.PedidoDAOImpl;
import ojoaldato.modelo.Pedido;

import java.util.List;

public class TestPedidosEnviados {
    public static void main(String[] args) {
        PedidoDAO pedidoDAO = new PedidoDAOImpl();

        List<Pedido> pedidos = pedidoDAO.obtenerEnviados();

        System.out.println("Todos los pedidos enviados:");
        System.out.println("-------------------------------------------");

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos enviados en la base de datos.");
        } else {
            for (Pedido p : pedidos) {
                System.out.println("Num. pedido:  " + p.getNumPedido());
                System.out.println("Cliente:      " + p.getCliente().getEmail());
                System.out.println("Artículo:     " + p.getArticulo().getCodigo());
                System.out.println("Cantidad:     " + p.getCantidad());
                System.out.println("Fecha/Hora:   " + p.getFechaHora());
                System.out.println("Importe total:" + p.calcularImporteTotal());
                System.out.println("-------------------------------------------");
            }
        }
    }
}
