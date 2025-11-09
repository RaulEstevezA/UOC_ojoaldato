package ojoaldato.DAO;

import ojoaldato.modelo.Pedido;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD básicas
 * @param <T> Tipo de la entidad con la que trabaja el DAO
 * @param <K> Es como una variable que representa "el tipo de dato que usaremos como ID
 */
public interface IDAO<T, K> {
    
    /**
     * Crea un nuevo registro en la base de datos
     * @param entidad La entidad a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    boolean crear(T entidad);
    
    /**
     * Busca una entidad por su email
     * @param id El email a buscar
     * @return La entidad encontrada o null si no existe
     */
    T buscar(K id);

    /**
     * Actualiza una entidad existente
     * @param entidad La entidad con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean actualizar(T entidad);
    
    /**
     * Elimina una entidad por su identificador
     * @param id El identificador de la entidad a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean eliminar(K id);
    
    /**
     * Obtiene todas las entidades
     * @return Lista con todas las entidades
     */
    List<T> obtenerTodos() throws SQLException;
}
