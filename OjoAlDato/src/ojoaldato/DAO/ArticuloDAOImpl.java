package ojoaldato.DAO;

import ojoaldato.db.JpaUtil;
import ojoaldato.excepcion.ElementoDuplicadoException;
import ojoaldato.excepcion.ElementoNoEncontradoException;
import ojoaldato.modelo.Articulo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ArticuloDAOImpl implements ArticuloDAO {

    @Override
    public boolean crear(Articulo a) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // Comprobación de duplicado igual que en JDBC:
            Articulo existente = em.find(Articulo.class, a.getCodigo());
            if (existente != null) {
                throw new ElementoDuplicadoException(
                        "El artículo con código " + a.getCodigo() + " ya existe."
                );
            }

            em.persist(a);
            em.getTransaction().commit();
            return true;

        } catch (ElementoDuplicadoException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al crear artículo.", e);
        }
    }

    @Override
    public Articulo buscar(String codigo) {
        EntityManager em = JpaUtil.getEntityManager();

        Articulo a = em.find(Articulo.class, codigo);
        if (a == null) {
            throw new ElementoNoEncontradoException(
                    "Artículo con código " + codigo + " no encontrado."
            );
        }

        return a;
    }

    @Override
    public boolean actualizar(Articulo a) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Articulo original = em.find(Articulo.class, a.getCodigo());
            if (original == null) {
                throw new ElementoNoEncontradoException(
                        "Artículo con código " + a.getCodigo() + " no encontrado."
                );
            }

            // Solo actualizamos campos no nulos
            if (a.getDescripcion() != null && !a.getDescripcion().isBlank()) {
                original.setDescripcion(a.getDescripcion());
            }

            if (a.getPvp() != null) {
                original.setPvp(a.getPvp());
            }

            if (a.getGastosEnvio() != null) {
                original.setGastosEnvio(a.getGastosEnvio());
            }

            if (a.getTiempoPreparacion() != null) {
                original.setTiempoPreparacion(a.getTiempoPreparacion());
            }

            if (a.getStock() != null) {
                original.setStock(a.getStock());
            }

            if (a.getActivo() != null) {
                original.setActivo(a.getActivo());
            }

            em.merge(original);
            em.getTransaction().commit();
            return true;

        } catch (ElementoNoEncontradoException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al actualizar artículo.", e);
        }
    }

    @Override
    public boolean eliminar(String codigo) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Articulo a = em.find(Articulo.class, codigo);
            if (a == null) {
                throw new ElementoNoEncontradoException(
                        "No se encontró ningún artículo con el código: " + codigo
                );
            }

            em.remove(a);
            em.getTransaction().commit();
            return true;

        } catch (ElementoNoEncontradoException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public List<Articulo> obtenerTodos() {
        EntityManager em = JpaUtil.getEntityManager();

        TypedQuery<Articulo> query =
                em.createQuery("SELECT a FROM Articulo a", Articulo.class);

        return query.getResultList();
    }

    @Override
    public Articulo buscarArticuloPorDescripcion(String descripcion) {
        EntityManager em = JpaUtil.getEntityManager();

        TypedQuery<Articulo> query =
                em.createQuery("SELECT a FROM Articulo a WHERE a.descripcion LIKE :desc", Articulo.class);
        query.setParameter("desc", "%" + descripcion + "%");

        List<Articulo> lista = query.getResultList();

        if (lista.isEmpty()) {
            throw new ElementoNoEncontradoException("Artículo no encontrado.");
        }

        return lista.getFirst();
    }

    @Override
    public List<Articulo> buscarPorDescripcion(String descripcion) {
        EntityManager em = JpaUtil.getEntityManager();

        TypedQuery<Articulo> query =
                em.createQuery("SELECT a FROM Articulo a WHERE a.descripcion LIKE :desc", Articulo.class);
        query.setParameter("desc", "%" + descripcion + "%");

        return query.getResultList();
    }
}
