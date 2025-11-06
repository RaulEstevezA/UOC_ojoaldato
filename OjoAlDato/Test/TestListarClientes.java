package Test;

import DAO.ClienteDAO;
import ojoaldato.modelo.Cliente;

import java.util.List;

public class TestListarClientes {

    public static void main(String[] args) {

        ClienteDAO dao = new ClienteDAO();

        System.out.println("=== TEST: LISTAR CLIENTES ===");

        // 1 Listar todos los clientes
        List<Cliente> todos = dao.obtenerTodos();
        System.out.println("\n\n\n--- TODOS LOS CLIENTES ---");
        if (todos.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            todos.forEach(System.out::println);
        }

        // 2 Listar solo los clientes estándar
        List<Cliente> estandar = dao.obtenerEstandar();
        System.out.println("\n\n\n--- CLIENTES ESTÁNDAR ---");
        if (estandar.isEmpty()) {
            System.out.println("No hay clientes estándar registrados.");
        } else {
            estandar.forEach(System.out::println);
        }

        // 3 Listar solo los clientes premium
        List<Cliente> premium = dao.obtenerPremium();
        System.out.println("\n\n\n--- CLIENTES PREMIUM ---");
        if (premium.isEmpty()) {
            System.out.println("No hay clientes premium registrados.");
        } else {
            premium.forEach(System.out::println);
        }

        System.out.println("====================================");
    }
}
