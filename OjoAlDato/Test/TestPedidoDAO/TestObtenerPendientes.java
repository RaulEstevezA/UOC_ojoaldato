package TestPedidoDAO;

import ojoaldato.DAO.PedidoDAO;
import ojoaldato.DAO.PedidoDAOImpl;
import ojoaldato.modelo.Pedido;

import java.util.List;

public class TestObtenerPendientes {
    public static void main(String[] args) {
        PedidoDAO pedidoDAO = new PedidoDAOImpl();
        List<Pedido> pendientes = pedidoDAO.obtenerPendientes();

        System.out.println("Pedidos pendientes: " + pendientes.size());
        System.out.println("-------------------------------------------");
        for (Pedido p : pendientes) {
            System.out.println("Num. pedido: " + p.getNumPedido());
            System.out.println("Cliente:     " + p.getCliente().getEmail());
            System.out.println("Artículo:    " + p.getArticulo().getCodigo());
            System.out.println("Cantidad:    " + p.getCantidad());
            System.out.println("Fecha/Hora:  " + p.getFechaHora());
            System.out.println("-------------------------------------------");
        }
    }
}