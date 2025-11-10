package ojoaldato.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final String CONFIG_FILE = "db.properties";
    private final Properties properties;
    private static ConfigLoader instance;

    private ConfigLoader() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.err.println("Error: No se encuentra el archivo " + CONFIG_FILE + " en el classpath.");
                throw new RuntimeException("Archivo de configuración no encontrado.");
            }
            properties.load(input);
        } catch (IOException ex) {
            System.err.println("Error al cargar el archivo de propiedades: " + ex.getMessage());
            throw new RuntimeException("Fallo al leer las propiedades.", ex);
        }
    }

    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
