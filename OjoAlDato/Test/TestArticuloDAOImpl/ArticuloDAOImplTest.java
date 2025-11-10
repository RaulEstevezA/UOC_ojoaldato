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

}
