package ojoaldato.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase sencilla para gestionar la conexión a la base de datos MySQL.
 * Utiliza el patrón Singleton para garantizar una única conexión activa.
 */
public class ConexionDB {

    private static final ConfigLoader CONFIG = ConfigLoader.getInstance();

    private static final String URL = CONFIG.getProperty("db.url");
    private static final String USER = CONFIG.getProperty("db.user");
    private static final String PASSWORD = CONFIG.getProperty("db.password");
    private static final String DRIVER = CONFIG.getProperty("db.driver");

    private static Connection connection = null;

    static {
        try {
            // Cargar el driver de MySQL
            Class.forName(DRIVER);
            System.out.println("Driver de MySQL cargado correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver de MySQL: " + e.getMessage());
            throw new RuntimeException("No se pudo cargar el driver de MySQL.", e);
        }
    }

    private ConexionDB() {
        // Constructor privado para evitar instanciación directa
    }

    /**
     * Obtiene una conexión activa a la base de datos.
     *
     * @return Conexión JDBC activa
     * @throws SQLException si ocurre un error al conectar
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida correctamente.");
        }
        return connection;
    }

    /**
     * Cierra la conexión abierta, si existe.
     *
     * @param conn conexión a cerrar
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    /**
     * Método de prueba para verificar la conexión.
     */
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE CONEXIÓN ===");
        try (Connection conn = ConexionDB.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Conexión establecida correctamente con la base de datos.");
            }
        } catch (Exception e) {
            System.err.println("❌ Error al conectar con la base de datos:");
            e.printStackTrace();
        }
    }
}

