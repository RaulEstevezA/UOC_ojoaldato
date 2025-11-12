package ojoaldato.DAO;



/* ESTE FACTOR YHAY QUE MODIFICARLO CUANDO ESTEN LOS DAO TERMIANDOS
  RAUL TRANQUILO, LOS COMENTARIOS SON PARA ACLARARME MEJOR, DESPUÉS SE PUEDEN QUITAR TQ(.)
*/

public class Factory {

// Instancias únicas de cada DAO (patrón Singleton)
    private static ClienteDAO clienteDAO = null;
    private static ArticuloDAO articuloDAO = null;
    private static PedidoDAO pedidoDAO = null;

    //Constructor privado para evitar instanciación fuera de la clase
    private Factory() {}

    //He visto que se puede usar synchronized para evitar que se puedan crear dos instancias al mismo tiempo, pero no tenemos múltiples hilos
    //Método para obtener la instancia única de cada DAO
    public static ClienteDAO getClienteDAO() {
        if (clienteDAO == null) {
            clienteDAO = new ClienteDAOImpl();
        }
        return clienteDAO;
    }

    public static ArticuloDAO getArticuloDAO() {
        if (articuloDAO == null) {
            articuloDAO = new ArticuloDAOImpl();
        }
        return articuloDAO;
    }

    public static PedidoDAO getPedidoDAO() {
        if (pedidoDAO == null) {
            pedidoDAO = new PedidoDAOImpl();
        }
        return pedidoDAO;
    }

    
}
