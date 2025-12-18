package ojoaldato.DAO;

import ojoaldato.db.JpaUtil;
import ojoaldato.excepcion.ElementoDuplicadoException;
import ojoaldato.excepcion.ElementoNoEncontradoException;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.ClientePremium;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.List;

/**
 * Implementación concreta del DAO para la entidad Cliente.
 * Gestiona las operaciones CRUD sobre la tabla 'clientes' en MySQL.
 */
public class ClienteDAOImpl implements ClienteDAO {

    // MÉTODOS CRUD (IDAO)
    @Override
    public boolean crear(Cliente c) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // Comprobación duplicado
            Cliente existente = em.find(Cliente.class, c.getEmail());
            if (existente != null) {
                throw new ElementoDuplicadoException(
                        "Ya existe un cliente con email " + c.getEmail());
            }

            em.persist(c);
            em.getTransaction().commit();
            return true;

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public Cliente buscar(String email) {
        EntityManager em = JpaUtil.getEntityManager();
        Cliente c = em.find(Cliente.class, email);

        if (c == null) {
            throw new ElementoNoEncontradoException(
                    "No existe ningún cliente con email " + email);
        }

        return c;
    }

    @Override
    public boolean actualizar(Cliente c) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Cliente original = em.find(Cliente.class, c.getEmail());
            if (original == null) {
                throw new ElementoNoEncontradoException(
                        "Cliente con email " + c.getEmail() + " no encontrado."
                );
            }

            if (!original.getClass().equals(c.getClass())) {
                em.remove(original);
                em.persist(c);
                em.getTransaction().commit();
                return true;
            }

            if (c.getNombre() != null) original.setNombre(c.getNombre());
            if (c.getDomicilio() != null) original.setDomicilio(c.getDomicilio());
            if (c.getNif() != null) original.setNif(c.getNif());
            if (c.getCuota() != null) original.setCuota(c.getCuota());

            em.merge(original);
            em.getTransaction().commit();
            return true;

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public boolean eliminar(String email) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Cliente c = em.find(Cliente.class, email);
            if (c == null) {
                throw new ElementoNoEncontradoException(
                        "Cliente con email " + email + " no encontrado."
                );
            }

            em.remove(c);
            em.getTransaction().commit();
            return true;

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<Cliente> obtenerTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Cliente> query =
                em.createQuery("SELECT c FROM Cliente c ORDER BY c.nombre", Cliente.class);
        return query.getResultList();
    }

    // MÉTODOS ESPECÍFICOS
    @Override
    public List<Cliente> obtenerEstandar() {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Cliente> query =
                em.createQuery("SELECT c FROM ClienteEstandar c", Cliente.class);
        return query.getResultList();
    }

    @Override
    public List<Cliente> obtenerPremium() {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Cliente> query =
                em.createQuery("SELECT c FROM ClientePremium c", Cliente.class);
        return query.getResultList();
    }

    @Override
    public boolean existe(String email) {
        EntityManager em = JpaUtil.getEntityManager();
        Cliente c = em.find(Cliente.class, email);
        return c != null;
    }
}
