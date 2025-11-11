package TestArticuloDAOImpl;

import ojoaldato.DAO.ArticuloDAO.ArticuloDAO;
import ojoaldato.DAO.ArticuloDAO.ArticuloDAOImpl;
import ojoaldato.excepcion.ElementoDuplicadoException;
import ojoaldato.modelo.Articulo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ArticuloDAOImplTest {
    private ArticuloDAO dao;
    private Articulo articuloTest;

    @BeforeEach
    void setUp() {
        dao = new ArticuloDAOImpl();
        articuloTest = new Articulo("A001", "Artículo de prueba", BigDecimal.valueOf(9.99), BigDecimal.valueOf(2.99), 199, 90);
        dao.eliminar("A001");
    }

    @AfterEach
    void tearDown() {
        dao.eliminar("A001");
    }

    @Test
    @DisplayName("Debe crear un nuevo artículo correctamente")
    void testCrear() {
        // Primer intento: debería crearse sin problema
        boolean resultado = dao.crear(articuloTest);
        assertTrue(resultado, "El método crear() debería devolver true si se inserta correctamente.");

        assertThrows(
                ElementoDuplicadoException.class,
                () -> dao.crear(articuloTest),
                "Debería lanzar ElementoDuplicadoException si el código existe."
        );
    }

    @Test
    @DisplayName("Debe buscar un artículo existente por su código.")
    void testBuscar() {
        dao.crear(articuloTest);
        Articulo encontrado = dao.buscar("A001");

        assertNotNull(encontrado);
        assertEquals("Artículo de prueba", encontrado.getDescripcion());
    }

    @Test
    @DisplayName("Debe actualizar un artículo existente.")
    void testActualizar() {
        // Crear artículo inicial
        dao.crear(articuloTest);

        // Crear un nuevo objeto Artículo con solo algunos campos modificados
        Articulo modificado = new Articulo();
        modificado.setCodigo("A001");
        modificado.setDescripcion("Artículo actualizado");
        modificado.setPvp(BigDecimal.valueOf(12.99));

        // Ejecutar la actualización
        boolean resultado = dao.actualizar(modificado);
        assertTrue(resultado, "El método actualizar() debería volver true si se actualiza correctamente.");

        // Recuperar el artículo actualizado de la base de datos
        Articulo actualizado = dao.buscar("A001");

        // Verificar campos actualizados
        assertEquals("Artículo actualizado", actualizado.getDescripcion());
        assertEquals(BigDecimal.valueOf(12.99), actualizado.getPvp());

        // Verificar campos no modificados -> deben permanecer iguales
        assertEquals(articuloTest.getGastosEnvio(), actualizado.getGastosEnvio());
        assertEquals(articuloTest.getTiempoPreparacion(), actualizado.getTiempoPreparacion());
        assertEquals(articuloTest.getStock(), actualizado.getStock());

    }

}
