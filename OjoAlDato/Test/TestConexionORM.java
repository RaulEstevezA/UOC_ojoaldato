import ojoaldato.db.JpaUtil;
import javax.persistence.EntityManager;

public class TestConexionORM {
    public static void main(String[] args) {
        try {
            EntityManager em = JpaUtil.getEntityManager();
            System.out.println("Conexión JPA OK: " + (em != null));
            em.close();
            JpaUtil.close();
        } catch (Exception e) {
            System.out.println("Error en la conexión JPA:");
            e.printStackTrace();
        }
    }
}
