package ojoaldato.controlador;

import ojoaldato.modelo.Articulo;
import ojoaldato.modelo.Datos;
import ojoaldato.exception.ElementoNoEncontradoException;
import ojoaldato.exception.ElementoDuplicadoException;

import java.util.List;

/**
 * Clase controladora encargada de gestionar las operaciones relacionadas con los artículos.
 * Forma parte de la capa CONTROLADOR del patrón MVC.
 *
 * Esta clase no almacena directamente los artículos,
 * sino que utiliza el modelo (Datos) para acceder y modificar la información.
 */


public class ArticuloControlador {
    private Datos datos;

    /**
    @param datos objeto del modelo que gestiona la información del sistema
     * @throws ElementoDuplicadoException si el artículo ya existe.
     */
    public void agregarArticulo(Articulo articulo) {
        try {
            datos.agregarArticulo(articulo);
            System.out.println("Artículo agregado correctamente: " + articulo.getCodigo());
        } catch (ElementoDuplicadoException e) {
            // Mensaje claro que luego se reemplazará por alerta en JavaFX
            System.out.println("Error al agregar artículo: " + e.getMessage());
            throw e; // Re-lanzamos para que la vista pueda manejarlo también
        }
    }

    /**
     * Elimina un artículo del sistema a partir de su código.
     *
     * @param codigo código identificador del artículo a eliminar
     * @throws ElementoNoEncontradoException si el artículo no existe.
     */
    public void eliminarArticulo(String codigo) {
        try {
            Articulo articulo = datos.buscarArticulo(codigo);
            datos.eliminarArticulo(articulo);
            System.out.println("Artículo eliminado correctamente: " + codigo);
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Error al eliminar artículo: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca un artículo en el sistema a partir de su código.
     *
     * @param codigo código identificador del artículo
     * @return el objeto Articulo si se encuentra, o null si no existe
     * @throws ElementoNoEncontradoException si no se encuentra el artículo.
     */
    public Articulo buscarArticulo(String codigo) {
        try {
            return datos.buscarArticulo(codigo);
        } catch (ElementoNoEncontradoException e) {
            System.out.println("Error al buscar artículo: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Devuelve una lista con todos los artículos registrados en el sistema.
     *
     * @return lista de artículos actuales
     */
    public List<Articulo> listarArticulos() {
        return datos.listarArticulo();
    }

}
