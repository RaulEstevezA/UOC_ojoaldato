package ojoaldato.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JpaUtil {

    private static final String PERSISTENCE_UNIT = "OjoAlDatoPU";
    private static EntityManagerFactory emf;

    // Carga de propiedades desde db.properties
    private static Map<String, String> loadDatabaseProperties() {
        Properties props = new Properties();

        try (InputStream input = JpaUtil.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException("No se encuentra db.properties en Resources/");
            }

            props.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Error al cargar db.properties", e);
        }

        // Mapa que sobrescribirá las propiedades del persistence.xml
        Map<String, String> jpaProps = new HashMap<>();
        jpaProps.put("javax.persistence.jdbc.url", props.getProperty("db.url"));
        jpaProps.put("javax.persistence.jdbc.user", props.getProperty("db.user"));
        jpaProps.put("javax.persistence.jdbc.password", props.getProperty("db.password"));
        jpaProps.put("javax.persistence.jdbc.driver", props.getProperty("db.driver"));

        return jpaProps;
    }

    // Inicialización del EntityManagerFactory
    private static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            Map<String, String> props = loadDatabaseProperties();
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, props);
        }
        return emf;
    }

    // Obtener un EntityManager
    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    // Cerrar el EntityManagerFactory al finalizar la aplicación
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
