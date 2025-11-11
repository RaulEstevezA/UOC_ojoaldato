package ojoaldato.DAO.ArticuloDAO;

import ojoaldato.db.ConexionDB;
import ojoaldato.excepcion.ElementoDuplicadoException;
import ojoaldato.excepcion.ElementoNoEncontradoException;
import ojoaldato.modelo.Articulo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAOImpl implements ArticuloDAO {

    /**
     * Ejecuta una sentencia SELECT y devuelve una lista de Articulo.
     *
     * @param sql La sentencia SELECT a ejecutar.
     * @param params
     * @return
     */
    private List<Articulo> ejecutaSQLSelect(String sql, Object... params) {
        List<Articulo> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    Articulo a = new Articulo();
                    a.setCodigo(rs.getString("codigo"));
                    a.setDescripcion(rs.getString("descripcion"));
                    a.setPvp(rs.getBigDecimal("pvp"));
                    a.setGastosEnvio(rs.getBigDecimal("gastos_envio"));
                    a.setTiempoPreparacion(rs.getInt("tiempo_preparacion"));
                    a.setStock(rs.getInt("stock"));
                    a.setActivo(rs.getBoolean("activo"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al ejecutar SELECT", e);
        }

        return lista;
    }

    /**
     * Ejecuta sentencias SQL a ejecutar. Debe ser un INSERT, UPDATE o DELETE utilizando {@link PreparedStatement}.
     * Centraliza la ejecución de sentencias de modificaciones evitando duplicación de código.
     *
     * @param sql La sentencia SQL a ejecutar. Debe ser un INSERT, UPDATE o DELETE válido.
     * @param params Parámetros opcionales que reemplazarán los marcadores ? en la sentencia SQL, en orden.
     * @return El número de filas afectadas por la sentencia ejecutada.
     * @throws SQLException Si ocurre un error al conectar con la base de datos, preparar o ejecutar la sentencia.
     */
    private int ejecutaSQLInUpDel(String sql, Object... params) throws SQLException {
        try (Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            return stmt.executeUpdate();
        }
    }

    @Override
    public boolean crear(Articulo a) {
        String sql = "INSERT INTO articulos (codigo, descripcion, pvp, gastos_envio, tiempo_preparacion, stock) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            int filas = ejecutaSQLInUpDel(sql,
                    a.getCodigo(),
                    a.getDescripcion(),
                    a.getPvp(),
                    a.getGastosEnvio(),
                    a.getTiempoPreparacion(),
                    a.getStock()
            );
            return filas > 0;
        } catch (SQLException e) {
            if (e.getSQLState() != null && (e.getSQLState().equals("23000") || e.getErrorCode() == 1062)) {
                throw new ElementoDuplicadoException(
                        "El artículo con código " + a.getCodigo() + " ya existe."
                );
            } else {
                throw new RuntimeException("Error al crear artículo.", e);
            }
        }
    }

    @Override
    public Articulo buscar(String codigo) {
        String sql = "SELECT codigo, descripcion, pvp, gastos_envio, tiempo_preparacion, stock, activo " +
                "FROM articulos WHERE codigo = ?";

        List<Articulo> resultados = ejecutaSQLSelect(sql, codigo);

        if(resultados.isEmpty()) {
            throw new ElementoNoEncontradoException("Artículo con código " + codigo + " no encontrado.");
        }

        return resultados.getFirst();
    }

    @Override
    public boolean actualizar(Articulo a) {
        Articulo original = buscar(a.getCodigo());
        if (original == null) {
            throw new ElementoNoEncontradoException("Artículo con código " + a.getCodigo() + " no encontrado.");
        }

            if (a.getDescripcion() != null && !a.getDescripcion().isBlank()) {
                original.setDescripcion(a.getDescripcion());
            }

            if (a.getPvp() != null) {
                original.setPvp(a.getPvp());
            }

            if (a.getGastosEnvio() != null) {
                original.setGastosEnvio(a.getGastosEnvio());
            }

            if (a.getTiempoPreparacion() != null) {
                original.setTiempoPreparacion(a.getTiempoPreparacion());
            }

            if (a.getStock() != null) {
                original.setStock(a.getStock());
            }

            if (a.getActivo() != null) {
                original.setActivo(a.getActivo());
            }

            String sql = "UPDATE articulos SET descripcion = ?, pvp = ?, gastos_envio = ?, " +
                    "tiempo_preparacion = ?, stock = ?, activo = ? WHERE codigo = ?";

            try {
                int filasAfectadas = ejecutaSQLInUpDel(sql,
                        original.getDescripcion(),
                        original.getPvp(),
                        original.getGastosEnvio(),
                        original.getTiempoPreparacion(),
                        original.getStock(),
                        original.getActivo(),
                        original.getCodigo()
                );

                return filasAfectadas > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

    }

    @Override
    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM articulos WHERE codigo = ?";
        try {
            ejecutaSQLInUpDel(sql, codigo);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Articulo> obtenerTodos() {
        String sql = "SELECT codigo, descripcion, pvp, gastos_envio, tiempo_preparacion, stock, activo FROM articulos";

        List<Articulo> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Articulo a = new Articulo();
                    a.setCodigo(rs.getString("codigo"));
                    a.setDescripcion(rs.getString("descripcion"));
                    a.setPvp(rs.getBigDecimal("pvp"));
                    a.setGastosEnvio(rs.getBigDecimal("gastos_envio"));
                    a.setTiempoPreparacion(rs.getInt("tiempo_preparacion"));
                    lista.add(a);
                }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener todos los artículos.", e);
        }

        return lista;

    }

    @Override
    public Articulo buscarArticuloPorDescripcion(String descripcion) {
        String sql = "SELECT codigo, descripcion, pvp, gastos_envio, tiempo_preparacion, stock, activo " +
                "FROM articulos WHERE descripcion LIKE ?";

        List<Articulo> lista = ejecutaSQLSelect(sql, "%" + descripcion + "%");

        if (lista.isEmpty()) {
            throw new ElementoNoEncontradoException("Artículo no encontrado,");
        }

        return lista.getFirst();
    }

    @Override
    public List<Articulo> buscarPorDescripcion(String descripcion) {
        String sql = "SELECT codigo, descripcion, pvp, gastos_envio, tiempo_preparacion, stock, activo " +
                "FROM articulos WHERE descripcion LIKE ?";

        List<Articulo> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + descripcion + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    Articulo a = new Articulo();
                    a.setCodigo(rs.getString("codigo"));
                    a.setDescripcion(rs.getString("descripcion"));
                    a.setPvp(rs.getBigDecimal("pvp"));
                    a.setGastosEnvio(rs.getBigDecimal("gastos_envio"));
                    a.setTiempoPreparacion(rs.getInt("tiempo_preparacion"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ElementoNoEncontradoException("Error al obtener todos los artículos.");
        }

        return lista;
     }
}
