package ojoaldato.DAO.ArticuloDAO;

import ojoaldato.DAO.IDAO;
import ojoaldato.modelo.Articulo;

import java.util.List;

public interface ArticuloDAO extends IDAO<Articulo, String> {
    Articulo buscarArticuloPorDescripcion(String descripcion);
    List<Articulo> buscarPorDescripcion(String descripcion);
}
