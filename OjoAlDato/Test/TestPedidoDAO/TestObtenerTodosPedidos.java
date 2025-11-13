package TestPedidoDAO;

import ojoaldato.DAO.PedidoDAO;
import ojoaldato.DAO.PedidoDAOImpl;
import ojoaldato.modelo.Pedido;

import java.util.List;

public class TestObtenerTodosPedidos {
    public static void main(String[] args) {
        PedidoDAO pedidoDAO = new PedidoDAOImpl();
        List<Pedido> pedidos = pedidoDAO.obtenerTodos();

        System.out.println("Pedidos encontrados: " + pedidos.size());
        for (Pedido p : pedidos) {
            System.out.println("-------------------------------------------");
            System.out.println("Num. pedido:  " + p.getNumPedido());
            System.out.println("Cliente:      " + (p.getCliente() != null ? p.getCliente().getEmail() : "N/A"));
            System.out.println("Artículo:     " + (p.getArticulo() != null ? p.getArticulo().getCodigo() : "N/A"));
            System.out.println("Cantidad:     " + p.getCantidad());
            System.out.println("Fecha/Hora:   " + p.getFechaHora());
            System.out.println("Importe total:" + p.calcularImporteTotal());
        }
    }
}
