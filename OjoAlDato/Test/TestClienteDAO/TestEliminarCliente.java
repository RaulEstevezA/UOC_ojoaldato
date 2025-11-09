package TestClienteDAO;

import ojoaldato.db.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestEliminarCliente {

    public static void main(String[] args) {

        String emailCliente1 = "maria@example.com";
        String emailCliente2 = "carlos@example.com";

        String sql = "DELETE FROM clientes WHERE email = ?";

        System.out.println("=== TEST: ELIMINAR CLIENTES (FÍSICO) ===");

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Borrar primer cliente
            ps.setString(1, emailCliente1);
            int filas1 = ps.executeUpdate();

            // Borrar segundo cliente
            ps.setString(1, emailCliente2);
            int filas2 = ps.executeUpdate();

            if (filas1 > 0)
                System.out.println("Cliente estándar eliminado: " + emailCliente1);
            else
                System.out.println("No se encontró el cliente estándar: " + emailCliente1);

            if (filas2 > 0)
                System.out.println("Cliente premium eliminado: " + emailCliente2);
            else
                System.out.println("No se encontró el cliente premium: " + emailCliente2);

        } catch (SQLException e) {
            System.err.println("Error al eliminar clientes: " + e.getMessage());
        }

        System.out.println("=========================================");
    }
}
