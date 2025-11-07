package ojoaldato.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * Clase de utilidad para gestionar la conexión a la base de datos MySQL.
 * Utiliza el patrón Singleton para asegurar una única instancia de conexión.
 */
public class ConexionDB {
    // Se cargan variables de entorno en vez de hardcodear los datos necesarios para la conexión en la BBDD
    private static final ConfigLoader CONFIG = ConfigLoader.getInstance();

    private static final String URL = CONFIG.getProperty("db.url");
    private static final String USER = CONFIG.getProperty("db.user");
    private static final String PASSWORD = CONFIG.getProperty("db.password");
    private static final String DRIVER = CONFIG.getProperty("db.driver");

    // Ruta al script de creación de tablas
    private static final String SCHEMA_PATH = CONFIG.getProperty("db.schema");
    private static final String DATA = CONFIG.getProperty("db.data");

    // Instancia Singleton de la conexión
    private static Connection connection = null;

    static {
        try {
            // Cargar el driver de MySQL
            Class.forName(DRIVER);
            System.out.println("Driver de MySQL cargado correctamente");

            // Ejecuta el script al cargar la clase y el driver
            executeSqlScript(SCHEMA_PATH, "Estructura de OjoAlDato");
            executeSqlScript(DATA, "Datos iniciales.");

        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver de MySQL");
            e.printStackTrace();
            throw new RuntimeException("No se pudo cargar el driver de MySQL", e);
        } catch (SQLException e) {
            System.err.println("Error SQL al inicializar la base de datos.");
            e.printStackTrace();
            throw new RuntimeException("Fallo en la ejecución del script SQL.", e);
        } catch (IOException e) {
            System.err.println("Error de lectura al cargar el script SQL.");
            e.printStackTrace();
            throw new RuntimeException("Fallo al leer el archivo de script.", e);
        }
    }

    // Constructor privado para el patrón Singleton
    private ConexionDB() {}

    /**
     * Obtiene una conexión a la base de datos.
     * @return Objeto Connection para interactuar con la base de datos
     * @throws SQLException Si ocurre un error al establecer la conexión
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida correctamente.");
        }
        return connection;
    }
    
    /**
     * Cierra la conexión a la base de datos.
     * @param conn La conexión a cerrar
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada correctamente");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión");
                e.printStackTrace();
            }
        }
    }

    /**
     * Lee y ejecuta el script SQL para crear las tablas.
     *
     * @throws SQLException
     * @throws IOException
     */
    private static void executeSqlScript(String scriptPath, String name) throws SQLException, IOException{
        System.out.println("Verificando la estructura de datos: " + name);

        // El script se lee a través del ClassLoader
        try (InputStream is = ConexionDB.class.getClassLoader().getResourceAsStream(scriptPath);
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement()) {
            if (is == null) {
                System.err.println("Advertencia: No se encontró el script de "  + name + " en la ruta: " + scriptPath);
                return;
            }

            // Lectura del script
            String sqlScript = new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n"));

            sqlScript = sqlScript.replaceAll("/\\*(./\\n)*?\\*/", "")
                    .replaceAll("--.*", "")
                    .replaceAll("\r\n|\r|\n", " ")
                    .replaceAll("\\s{2,}", " ")
                    .trim();

           stmt.execute(sqlScript);

            System.out.println("Script de inicialización ejecutado con éxito.");
        }

    }
}
