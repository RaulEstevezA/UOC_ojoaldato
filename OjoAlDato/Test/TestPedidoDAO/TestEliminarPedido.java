package TestPedidoDAO;

import ojoaldato.DAO.PedidoDAO;
import ojoaldato.DAO.PedidoDAOImpl;

public class TestEliminarPedido {
    public static void main(String[] args) {
        PedidoDAO pedidoDAO = new PedidoDAOImpl();
        int numPedido = 3;

        boolean eliminado = pedidoDAO.eliminar(numPedido);

        if (eliminado) {
            System.out.println("Pedido " + numPedido + " eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el pedido (ya se ha enviado o no existe). Número de pedido: " + numPedido);
        }
    }
}

