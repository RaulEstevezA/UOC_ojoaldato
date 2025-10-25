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
}
