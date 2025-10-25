package ojoaldato.controlador;

import ojoaldato.modelo.Articulo;
import ojoaldato.modelo.Datos;

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
    */

    public ArticuloControlador(Datos datos) {
        this.datos = datos;
    }

    public boolean agregarArticulo(Articulo articulo) {
        // Busca si ya existe un artículo con el mismo código
        if (datos.buscarArticulo(articulo.getCodigo()) == null) {
            // Si no existe, lo agrega al modelo
            datos.agregarArticulo(articulo);
            return true;
        } else {
            // Si ya existe, no se agrega
            return false;
        }
    }

    /**
     * Elimina un artículo del sistema a partir de su código.
     *
     * @param codigo código identificador del artículo a eliminar
     * @return true si se eliminó correctamente, false si no se encontró
     */
    public boolean eliminarArticulo(String codigo) {
        // Busca el artículo por su código en el modelo Datos
        Articulo articulo = datos.buscarArticulo(codigo);
        if (articulo != null) {
            // Si existe, se elimina
            datos.eliminarArticulo(articulo);
            return true;
        } else {
            // Si no existe, devuelve false
            return false;
        }
    }
}
