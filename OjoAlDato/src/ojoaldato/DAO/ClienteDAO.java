package ojoaldato.DAO;

import java.util.List;
import ojoaldato.modelo.Cliente;

/**
 * DAO específico para la entidad Cliente.
 * Extiende el perfil genérico IDAO<T,K> usando:
 *   T = Cliente
 *   K = String (email como clave primaria)
 */
public interface ClienteDAO extends IDAO<Cliente, String> {


     // Lista todos los clientes de tipo ESTÁNDAR.
    List<Cliente> obtenerEstandar();

    // Lista todos los clientes de tipo PREMIUM.
    List<Cliente> obtenerPremium();

    // Comprueba si existe un cliente por email.
    boolean existe(String email);
}