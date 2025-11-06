package Test;

import DAO.ClienteDAO;

public class TestDesactivarCliente {

    public static void main(String[] args) {

        ClienteDAO dao = new ClienteDAO();

        String emailCliente1 = "maria@example.com";
        String emailCliente2 = "carlos@example.com";

        System.out.println("=== TEST: ELIMINAR (DESACTIVAR) CLIENTES ===");

        boolean eliminado1 = dao.eliminar(emailCliente1);
        boolean eliminado2 = dao.eliminar(emailCliente2);

        if (eliminado1)
            System.out.println("Cliente estándar desactivado correctamente: " + emailCliente1);
        else
            System.out.println("Error al desactivar cliente estándar.");

        if (eliminado2)
            System.out.println("Cliente premium desactivado correctamente: " + emailCliente2);
        else
            System.out.println("Error al desactivar cliente premium.");

        System.out.println("=============================================");
    }
}
