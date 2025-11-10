package ojoaldato.controlador;

import ojoaldato.DAO.ArticuloDAO.ArticuloDAOImpl;
import ojoaldato.excepcion.ElementoDuplicadoException;
import ojoaldato.excepcion.ElementoNoEncontradoException;
import ojoaldato.modelo.Articulo;

import java.util.Collections;
import java.util.List;

/**
 * Clase controladora encargada de gestionar las operaciones relacionadas con los artículos.
 * Forma parte de la capa CONTROLADOR del patrón MVC.
 */


public class ArticuloControlador {
    private final ArticuloDAOImpl articuloDAO = new ArticuloDAOImpl();

    public ArticuloControlador() {}

    /**
     * Añade un artículo en el sistema
     *
     * @param a El artículo a añadir
     * @return Mensaje con resultado de la operación
     */
    public String addArticulo(Articulo a) {
        try {
            articuloDAO.crear(a);
            return "Artículo añadido correctamente en el sistema.";
        } catch (ElementoDuplicadoException e) {
            return "Artículo con código " + a.getCodigo() + " ya registrado";
        } catch (Exception e) {
            return "Error inesperado al añadir artículo.\n" + e.getMessage();
        }
    }

    /**
     * Devuelve una lista con todos los artículos registrados en el sistema.
     *
     * @return lista de artículos actuales
     */
    public List<Articulo> getAllArticulos() {
        try {
            return articuloDAO.obtenerTodos();
        } catch (Exception e) {
            System.err.println("Error al listar artículos.\n" + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Busca un artículo en el sistema a partir de su código.
     *
     * @param codigo Codigo identificador del artículo.
     * @return El objeto artículo si lo encuentra. Null si no existe.
     */
    public Articulo getArticuloByCodigo(String codigo) {
        try {
            return articuloDAO.buscar(codigo);
        } catch (ElementoNoEncontradoException e) {
            System.err.println("No se encontró ningún articulo con el código " + codigo);
            return null;
        }
    }

    /**
     * Elimina un artículo del sistema a partir de su código.
     *
     * @param codigo código identificador del artículo a eliminar
     * @return Mensaje con el resultado de la operación
     */
    public String deleteArticulo(String codigo) {
        try {
            Articulo articulo = articuloDAO.buscar(codigo);
            return "Artículo con código " + articulo.getCodigo() + " eliminado correctamente";
        } catch (ElementoNoEncontradoException e) {
            return "No se encontró ningún artículo con el código " + codigo;
        } catch (Exception e) {
            return "Error inesperado al eliminar el artículo.\n" + e.getMessage();
        }
    }
}
