package ojoaldato.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.stream.Collectors;

/**
 * Clase de utilidad para gestionar la conexión a la base de datos MySQL.
 * Utiliza el patrón Singleton para asegurar una única instancia de conexión.
 * Verifica automáticamente la estructura de la base de datos al inicializarse,
 * ejecutando los scripts SQL de creación de tablas, vistas y datos iniciales si fuera necesario.
 */
public class ConexionDB {
    // Cargador de configuración (patrón Singleton)
    private static final ConfigLoader CONFIG = ConfigLoader.getInstance();

    private static final String URL = CONFIG.getProperty("db.url");
    private static final String USER = CONFIG.getProperty("db.user");
    private static final String PASSWORD = CONFIG.getProperty("db.password");
    private static final String DRIVER = CONFIG.getProperty("db.driver");

    // Ruta al script de creación de tablas
    private static final String SCHEMA_PATH = CONFIG.getProperty("db.schema");
    private static final String VIEW_PATH = CONFIG.getProperty("db.view");
    private static final String DATA = CONFIG.getProperty("db.data");

    // Instancia única (Singleton) de la conexión principal
    private static Connection connection;

    // Bloque estático que se ejecuta al cargar la clase
    static {
        try {
            // 1. Carga dinámica el driver de MySQL
            Class.forName(DRIVER);
            System.out.println("Driver de MySQL cargado correctamente");

            // 2. Crear conexión temporal para inicializar y verificar estructura
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida con éxito.");

            // 3. Verificar si las tablas existen
            if (!estructuraCompleta(connection)) {
                System.out.println("Estructura incompleta. Ejecutando scripts iniciales...");
                // Crea la estructura inicial
                ejecutarScripts(SCHEMA_PATH);

                // 3.1 Comprueba si existe la vista
                if (VIEW_PATH != null && !VIEW_PATH.isEmpty()) {
                    ejecutarScripts(VIEW_PATH);
                }

                // 3.2 Comprueba si hay datos iniciales
                if (DATA != null & !DATA.isEmpty()) {
                    ejecutarScripts(DATA);
                }
            } else {
                System.out.println("Estructura de datos verificada correctamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error SQL al inicializar la base de datos.", e);
        }
    }

    // Constructor privado para el patrón Singleton
    private ConexionDB() {}

    /**
     * Obtiene una conexión a la base de datos.
     * Si no existe una conexión abierta o la anterior fue cerrada, se crea una nueva instancia de conexión.
     *
     * @return Objeto {@link Connection} listo para realizar operaciones SQL.
     * @throws SQLException Si ocurre un error al establecer la conexión.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida correctamente.");
        }
        return connection;
    }
    
    /**
     * Cierra una conexión específica de la base de datos.
     *
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
     * Verifica si la estructura básica de la base de datos ya está creada.
     * Comprueba la existencia de las tablas principales necesarias para la aplicación.
     *
     * @param conn Conexión activa a la base de datos.
     * @return {@code true} si todas las tablas requeridas existen; {@code false} en caso contrario.
     * @throws SQLException Si ocurre un error al consultar el metadato de la base de datos.
     */
    private static boolean estructuraCompleta(Connection conn) throws SQLException {
        String[] tablas = {"clientes", "articulos", "pedidos"};
        int contador = 0;

        for (String tabla : tablas) {
            try (ResultSet rs = conn.getMetaData().getTables(null, null, tabla, null)) {
                if (rs.next()) contador++;
            }
        }

        return contador == tablas.length;
    }

    /**
     * Ejecuta un archivo SQL ubicado en el classpath del proyecto.
     * Este método lee los archivos línea por línea, separa las sentencias por punto y coma,
     * y las ejecuta individualmente sobre la conexión activa.
     *
     * @param path Ruta relativa del archivo SQL
     * @throws IOException Si el archivo no puede ser leído o no se encuentra.
     * @throws SQLException Si alguna sentencia SQL falla durante la ejecució.
     */
    private static void ejecutarScripts(String path) throws IOException, SQLException {
        if (path == null) return;

        try (InputStream is = ConexionDB.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("No se encontró el archivo SQL: " + path);
            }

            String sql = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Dividir por punto y coma, ignorando líneas vacías
            String[] statements = sql.split(";");
            try (Statement stmt = connection.createStatement()) {
                for (String statement : statements) {
                    statement = statement.trim();
                    if (!statement.isEmpty()) {
                        stmt.execute(statement);
                    }
                }
            }

            System.out.println("Script ejecutado correctamente: " + path);
        }
    }
}
