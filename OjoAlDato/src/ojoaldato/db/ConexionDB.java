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

//    // Datos de conexión
//    private static final String URL = "jdbc:mysql://localhost:3306/ojoaldato";
//    private static final String USER = "equipo";
//    private static final String PASSWORD = "Equipo1234.";
//
    // Bloque estático para registrar el driver
//    static {
//        try {
//            // Cargar el driver de MySQL
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            System.out.println("Driver de MySQL cargado correctamente");
//        } catch (ClassNotFoundException e) {
//            System.err.println("Error al cargar el driver de MySQL");
//            e.printStackTrace();
//            throw new RuntimeException("No se pudo cargar el driver de MySQL", e);
//        }
//    }

    /**
     * Obtiene una conexión a la base de datos.
     * @return Objeto Connection para interactuar con la base de datos
     * @throws SQLException Si ocurre un error al establecer la conexión
     */
    public static Connection getConnection() throws SQLException {
        // Se añade el bloque estático al propio método getConnection() ya que ambos son estáticos
        try {
            // Cargar el driver de MySQL
            Class.forName(DRIVER);
            System.out.println("Driver de MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver de MySQL");
            e.printStackTrace();
            throw new RuntimeException("No se pudo cargar el driver de MySQL", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
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
     * ESTO HABRÁ QUE ELIMINARLO CUANDO A TODOS OS FUNCIONE CORRECTAMENTE :)
     * Método para probar la conexión a la base de datos.
     * @param args Argumentos de línea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        Connection conn = null;
        try {
            System.out.println("=== Prueba de conexión a MySQL ===");
            conn = getConnection();
            System.out.println("¡Conexión exitosa a la base de datos!");
            System.out.println("URL: " + URL);
            System.out.println("Usuario: " + USER);
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos:");
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("Código de error: " + e.getErrorCode());
            System.err.println("Estado SQL: " + e.getSQLState());
            e.printStackTrace();
        } finally {
            close(conn);
        }
    }
}
