package TestPedidoDAO;

import ojoaldato.DAO.PedidoDAO;
import ojoaldato.DAO.PedidoDAOImpl;
import ojoaldato.modelo.Pedido;

public class TestBuscarPorId {
    public static void main(String[] args) {
        PedidoDAO pedidoDAO = new PedidoDAOImpl();
        Integer id = 1;

        Pedido pedido = pedidoDAO.buscar(id);
        if (pedido != null) {
            System.out.println("Pedido encontrado: " + pedido.getNumPedido());
            System.out.println("Cliente: " + pedido.getCliente().getEmail());
            System.out.println("Artículo: " + pedido.getArticulo().getCodigo());
            System.out.println("Cantidad: " + pedido.getCantidad());
        } else {
            System.err.println("Pedido no encontrado: " + id);
        }
    }
}

