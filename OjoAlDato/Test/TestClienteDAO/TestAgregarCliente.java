// package Test;

import ojoaldato.DAO.ClienteDAO;
import ojoaldato.DAO.ClienteDAOImpl;
import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.ClientePremium;

public class TestAgregarCliente {

    public static void main(String[] args) {

        ClienteDAO dao = new ClienteDAOImpl();

        // Cliente estándar
        ClienteEstandar cliente1 = new ClienteEstandar(
                "María López",
                "Calle Mayor 15",
                "11223344A",
                "maria@example.com"
        );

        // Cliente premium
        ClientePremium cliente2 = new ClientePremium(
                "Carlos Ruiz",
                "Avenida Libertad 20",
                "99887766B",
                "carlos@example.com"
        );

        System.out.println("=== TEST: AGREGAR CLIENTES ===");
        System.out.println("Intentando añadir: " + cliente1.getEmail() + " y " + cliente2.getEmail());

        boolean agregado1 = dao.crear(cliente1);
        boolean agregado2 = dao.crear(cliente2);

        if (agregado1)
            System.out.println("Cliente estándar añadido correctamente: " + cliente1.getEmail());
        else
            System.out.println("Error al añadir cliente estándar.");

        if (agregado2)
            System.out.println("Cliente premium añadido correctamente: " + cliente2.getEmail());
        else
            System.out.println("Error al añadir cliente premium.");

        System.out.println("================================");
    }
}
