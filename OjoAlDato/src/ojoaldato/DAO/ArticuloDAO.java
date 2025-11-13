package ojoaldato.DAO;

import ojoaldato.modelo.Articulo;

import java.util.List;

public interface ArticuloDAO extends IDAO<Articulo, String> {
    Articulo buscarArticuloPorDescripcion(String descripcion);
    List<Articulo> buscarPorDescripcion(String descripcion);
}
