package ojoaldato.DAO;

import ojoaldato.db.JpaUtil;
import ojoaldato.excepcion.ElementoNoEncontradoException;
import ojoaldato.modelo.*;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {

    /**
     * Crea un nuevo pedido y actualiza el stock del artículo correspondiente.
     * Utiliza una transacción para garantizar la integridad de los datos.
     * 
     * @param pedido El pedido a crear
     * @return true si el pedido se creó correctamente, false en caso contrario
     * @throws RuntimeException Si ocurre un error al procesar el pedido
     */
    @Override
    public boolean crear(Pedido pedido) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Articulo articulo = em.find(Articulo.class, pedido.getArticulo().getCodigo());
            if (articulo == null) {
                throw new ElementoNoEncontradoException("El artículo del pedido no existe.");
            }

            int nuevoStock = articulo.getStock() - pedido.getCantidad();
            if (nuevoStock < 0) {
                throw new RuntimeException("Stock insuficiente.");
            }

            articulo.setStock(nuevoStock);
            em.persist(pedido);
            em.getTransaction().commit();
            return true;

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }

    //Eliminación de un pedido en la base de datos si no está enviado
    @Override
    public boolean eliminar(Integer numPedido) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Pedido p = em.find(Pedido.class, numPedido);
            if (p == null) {
                throw new ElementoNoEncontradoException("Pedido no encontrado.");
            }

            if (Boolean.TRUE.equals(p.getEnviado())) {
                throw new RuntimeException("No se puede eliminar un pedido ya enviado.");
            }

            em.remove(p);
            em.getTransaction().commit();
            return true;

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<Pedido> obtenerTodos() {
        actualizarPedidosAutomaticamente();
        EntityManager em = JpaUtil.getEntityManager();

        TypedQuery<Pedido> query = em.createQuery(
                "SELECT p FROM Pedido p", Pedido.class);
        return query.getResultList();
    }

    @Override
    public List<Pedido> buscarPorEmail(String email) {
        actualizarPedidosAutomaticamente();
        EntityManager em = JpaUtil.getEntityManager();

        TypedQuery<Pedido> query = em.createQuery(
                "SELECT p FROM Pedido p WHERE p.cliente.email = :email",
                Pedido.class);
        query.setParameter("email", email);

        return query.getResultList();
    }

    @Override
    public List<Pedido> obtenerEnviados() {
        actualizarPedidosAutomaticamente();
        EntityManager em = JpaUtil.getEntityManager();

        return em.createQuery(
                "SELECT p FROM Pedido p WHERE p.enviado = true",
                Pedido.class).getResultList();
    }

    @Override
    public List<Pedido> obtenerEnviadosPorEmail(String email) {
        actualizarPedidosAutomaticamente();
        EntityManager em = JpaUtil.getEntityManager();

        TypedQuery<Pedido> q = em.createQuery(
                "SELECT p FROM Pedido p WHERE p.enviado = true AND p.cliente.email = :email",
                Pedido.class);

        q.setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public List<Pedido> obtenerPendientes() {
        actualizarPedidosAutomaticamente();
        EntityManager em = JpaUtil.getEntityManager();

        return em.createQuery(
                "SELECT p FROM Pedido p WHERE p.enviado = false",
                Pedido.class).getResultList();
    }

    @Override
    public List<Pedido> obtenerPendientesPorEmail(String email) {
        actualizarPedidosAutomaticamente();
        EntityManager em = JpaUtil.getEntityManager();

        TypedQuery<Pedido> q = em.createQuery(
                "SELECT p FROM Pedido p WHERE p.enviado = false AND p.cliente.email = :email",
                Pedido.class);

        q.setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public Pedido buscar(Integer id) {
        EntityManager em = JpaUtil.getEntityManager();
        Pedido p = em.find(Pedido.class, id);

        if (p == null) {
            throw new ElementoNoEncontradoException(
                    "No existe el pedido con ID " + id);
        }

        return p;
    }

    @Override
    public boolean actualizar(Pedido pedido) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Pedido original = em.find(Pedido.class, pedido.getNumPedido());
            if (original == null) {
                throw new ElementoNoEncontradoException("Pedido no encontrado.");
            }

            // Cancelable?
            if (original.esCancelable(LocalDateTime.now())) {
                em.getTransaction().rollback();
                return false;
            }

            original.setEnviado(true);
            original.setFechaEnvio(LocalDateTime.now());

            em.merge(original);
            em.getTransaction().commit();
            return true;

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }


    @Override
    public void actualizarPedidosAutomaticamente() {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            List<Pedido> pendientes = em.createQuery(
                    "SELECT p FROM Pedido p WHERE p.enviado = false",
                    Pedido.class
            ).getResultList();

            LocalDateTime ahora = LocalDateTime.now();

            for (Pedido p : pendientes) {
                if (!p.esCancelable(ahora)) {
                    p.setEnviado(true);
                    p.setFechaEnvio(LocalDateTime.now());
                    em.merge(p);
                }
            }

            em.getTransaction().commit();

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        }
    }
}