package TestClienteDAO;

import ojoaldato.DAO.ClienteDAO;
import ojoaldato.DAO.ClienteDAOImpl;
import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.ClientePremium;

public class TestModificarCliente {

    public static void main(String[] args) {

        ClienteDAO dao = new ClienteDAOImpl();

        // Clientes existentes que vamos a modificar
        ClienteEstandar cliente1 = new ClienteEstandar(
                "María López García",          // nuevo nombre
                "Calle Mayor 25",              // nuevo domicilio
                "11223344A",                   // mismo NIF
                "maria@example.com"            // mismo email (clave primaria)
        );

        ClientePremium cliente2 = new ClientePremium(
                "Carlos Ruiz Fernández",       // nuevo nombre
                "Avenida Libertad 30",         // nuevo domicilio
                "99887766B",                   // mismo NIF
                "carlos@example.com"           // mismo email (clave primaria)
        );

        System.out.println("=== TEST: MODIFICAR CLIENTES ===");

        boolean modificado1 = dao.actualizar(cliente1);
        boolean modificado2 = dao.actualizar(cliente2);

        if (modificado1)
            System.out.println("Cliente estándar modificado correctamente: " + cliente1.getEmail());
        else
            System.out.println("Error al modificar cliente estándar.");

        if (modificado2)
            System.out.println("Cliente premium modificado correctamente: " + cliente2.getEmail());
        else
            System.out.println("Error al modificar cliente premium.");

        System.out.println("================================");
    }
}