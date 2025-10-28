package ojoaldato.controlador;

import ojoaldato.exception.ElementoDuplicadoException;
import ojoaldato.exception.ElementoNoEncontradoException;
import ojoaldato.modelo.Articulo;
import ojoaldato.modelo.Datos;

import java.util.Collections;
import java.util.List;

/**
 * Clase controladora encargada de gestionar las operaciones relacionadas con los artículos.
 * Forma parte de la capa CONTROLADOR del patrón MVC.
 *
 * Esta clase no almacena directamente los artículos,
 * sino que utiliza el modelo (Datos) para acceder y modificar la información.
 */


public class ArticuloControlador {
    private Datos datos = new Datos();

    public ArticuloControlador() {}

    /**
     * Añade un artículo en el sistema
     *
     * @param a El artículo a añadir
     * @return Mensaje con resultado de la operación
     */
    public String addArticulo(Articulo a) {
        try {
            datos.agregarArticulo(a);
            return "Artículo añadido correctamente en el sistema.";
        } catch (ElementoDuplicadoException e) {
            return "Artículo con código " + a.getCodigo() + " ya registrado";
        } catch (Exception e) {
            return "Error inesperado al añadir artículo.\n" + e.getMessage();
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
            Articulo articulo = datos.buscarArticulo(codigo);
            return "Artículo con código " + articulo.getCodigo() + " eliminado correctamente";
        } catch (ElementoNoEncontradoException e) {
            return "No se encontró ningún artículo con el código " + codigo;
        } catch (Exception e) {
            return "Error inesperado al eliminar el artículo.\n" + e.getMessage();
        }
    }

    /**
     * Busca un artículo en el sistema a partir de su código.
     *
     * @param codigo código identificador del artículo
     * @return el objeto Articulo si se encuentra, o null si no existe
     */

    public Articulo buscarArticulo(String codigo) {
        try {
            return datos.buscarArticulo(codigo);
        } catch (ElementoNoEncontradoException e) {
            System.err.println("Aviso: " + e.getMessage());
            return null;
        }
    }

    /**
     * Devuelve una lista con todos los artículos registrados en el sistema.
     *
     * @return lista de artículos actuales
     */
    public List<Articulo> listarArticulos() {
        try {
            return datos.listarArticulo();
        } catch (Exception e) {
            System.err.println("Error al listar artículos.\n" + e.getMessage());
            return Collections.emptyList();
        }
    }

}
