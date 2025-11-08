package ojoaldato.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    // Instancia Singleton de la conexión
    private static Connection connection = null;

    static {
        try {
            // Cargar el driver de MySQL
            Class.forName(DRIVER);
            System.out.println("Driver de MySQL cargado correctamente");

        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver de MySQL");
            e.printStackTrace();
            throw new RuntimeException("No se pudo cargar el driver de MySQL", e);
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
}
