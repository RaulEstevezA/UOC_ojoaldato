package ojoaldato.DAO;

import ojoaldato.modelo.Pedido;
import java.util.List;

public interface PedidoDAO extends IDAO<Pedido, Integer> {

    //Buscar un pedido por email
    List<Pedido> buscarPorEmail(String email);

    //Listar por pedidos enviados
    List<Pedido> obtenerEnviados();

    //Listar pedidos enviados filtrando por cliente
    List<Pedido> obtenerEnviadosPorEmail(String email);

    //Listar pedidos pendientes
    List<Pedido> obtenerPendientes();

    //Listar pedidos pendientes filtrando por cliente
    List<Pedido> obtenerPendientesPorEmail(String email);


}