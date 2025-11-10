package ojoaldato.DAO;

import ojoaldato.modelo.Pedido;
import java.util.List;

public interface PedidoDAO extends IDAO<Pedido, Integer> {

    //Buscar un pedido por email
    List<Pedido> buscarPorEmail(String email);

}