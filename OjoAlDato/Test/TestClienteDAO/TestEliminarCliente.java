package TestClienteDAO;

import ojoaldato.DAO.ClienteDAO;
import ojoaldato.DAO.FactoryDAO;
import ojoaldato.db.JpaUtil;
import ojoaldato.modelo.Cliente;

import javax.persistence.EntityManager;

public class TestEliminarCliente {

    public static void main(String[] args) {

        String emailCliente1 = "juan@example.com";
        String emailCliente2 = "juana@example.com";

        System.out.println("=== TEST: ELIMINAR CLIENTES (JPA/HIBERNATE) ===");

        // Obtenemos el DAO usando la Factory como siempre
        ClienteDAO clienteDAO = FactoryDAO.getClienteDAO();

        // El EntityManager lo gestionamos manualmente solo para la transacción del test
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // Buscar y borrar primer cliente
            Cliente c1 = buscarPorEmail(em, emailCliente1);
            if (c1 != null) {
                em.remove(c1);
                System.out.println("Cliente estándar eliminado: " + emailCliente1);
            } else {
                System.out.println("No se encontró el cliente estándar: " + emailCliente1);
            }

            // Buscar y borrar segundo cliente
            Cliente c2 = buscarPorEmail(em, emailCliente2);
            if (c2 != null) {
                em.remove(c2);
                System.out.println("Cliente premium eliminado: " + emailCliente2);
            } else {
                System.out.println("No se encontró el cliente premium: " + emailCliente2);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al eliminar clientes: " + e.getMessage());
        }

        System.out.println("==============================================");
    }

    /**
     * Busca un cliente por email usando una consulta JPQL.
     */
    private static Cliente buscarPorEmail(EntityManager em, String email) {
        try {
            return em.createQuery(
                            "SELECT c FROM Cliente c WHERE c.email = :email",
                            Cliente.class
                    ).setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // No encontrado
        }
    }
}
