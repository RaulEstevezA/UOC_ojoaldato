package TestArticuloDAOImpl;

import ojoaldato.DAO.ArticuloDAO;
import ojoaldato.DAO.ArticuloDAOImpl;
import ojoaldato.excepcion.ElementoDuplicadoException;
import ojoaldato.excepcion.ElementoNoEncontradoException;
import ojoaldato.modelo.Articulo;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArticuloDAOImplTest {
    private ArticuloDAO dao;
    private Articulo articuloTest1;
    private Articulo articuloTest2;

    @BeforeEach
    void setUp() {
        dao = new ArticuloDAOImpl();
        articuloTest1 = new Articulo("A001", "Artículo 1 de prueba", BigDecimal.valueOf(9.99), BigDecimal.valueOf(2.99), 199, 90);
        articuloTest2 = new Articulo("A002", "Artículo 2 de prueba", BigDecimal.valueOf(16.78), BigDecimal.valueOf(3.55), 120, 50);
        try { dao.eliminar("A001"); } catch (Exception ignored) {}
        try { dao.eliminar("A002"); } catch (Exception ignored) {}
        try { dao.eliminar("A003"); } catch (Exception ignored) {}

        dao.crear(articuloTest1);
        dao.crear(articuloTest2);
    }

    @AfterEach
    void tearDown() {
        try { dao.eliminar("A001"); } catch (Exception ignored) {}
        try { dao.eliminar("A002"); } catch (Exception ignored) {}
        try { dao.eliminar("A003"); } catch (Exception ignored) {}
    }

    @Test
    @Order(1)
    @DisplayName("Debe crear un nuevo artículo correctamente")
    void testCrear() {
        // Primer intento: debería crearse sin problema
        Articulo nuevo = new Articulo("A003", "Artículo temporal", BigDecimal.valueOf(5.0), BigDecimal.valueOf(1.0), 50, 20);
        boolean resultado = dao.crear(nuevo);
        assertTrue(resultado, "El método crear() debería devolver true si se inserta correctamente.");

        assertThrows(
                ElementoDuplicadoException.class,
                () -> dao.crear(nuevo),
                "Debería lanzar ElementoDuplicadoException si el código existe."
        );
    }

    @Test
    @Order(2)
    @DisplayName("Debe buscar un artículo existente por su código.")
    void testBuscar() {
        Articulo encontrado = dao.buscar("A001");

        assertNotNull(encontrado);
        assertEquals("Artículo 1 de prueba", encontrado.getDescripcion());
    }

    @Test
    @Order(3)
    @DisplayName("Debe actualizar un artículo existente.")
    void testActualizar() {
        // Buscar artículo existente y modificar solo algunos campos
        Articulo modificado = dao.buscar("A001");
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
        assertEquals(articuloTest1.getGastosEnvio(), actualizado.getGastosEnvio());
        assertEquals(articuloTest1.getTiempoPreparacion(), actualizado.getTiempoPreparacion());
        assertEquals(articuloTest1.getStock(), actualizado.getStock());
    }

    @Test
    @Order(4)
    @DisplayName("Debe eliminar un artículo existente.")
    void testEliminar() {
        Articulo encontrado = dao.buscar("A001");
        assertNotNull(encontrado, "El artículo debería existir antes de eliminarlo.");

        boolean resultado = dao.eliminar("A001");
        assertTrue(resultado, "Debe devolver true al eliminar un artículo existente.");

        assertThrows(
                ElementoNoEncontradoException.class,
                () -> dao.buscar("A001"),
                "Debe lanzar ElementoNoEncontradoException al intentar buscar un artículo eliminado."
        );
    }

    @Test
    @Order(5)
    @DisplayName("Debe devolver false si intenta eliminar un artículo inexistente.")
    void testEliminarInexistente() {
        assertThrows(ElementoNoEncontradoException.class, () -> dao.eliminar("ZZZ9999"), "Debe lanzar ElementoNoEncontradoException al intentar eliminar un artículo inexistente.");
    }

    @Test
    @Order(6)
    @DisplayName("Debe devolver todos los artículos existentes.")
    void testObtenerTodos() {
        List<Articulo> todos = dao.obtenerTodos();

        assertNotNull(todos);
        assertTrue(todos.size() >= 2, "La lista debería contener al menos los artículos insertados.");

        boolean contieneA001 = todos.stream().anyMatch(a -> "A001".equals(a.getCodigo()));
        boolean contieneA002 = todos.stream().anyMatch(a -> "A002".equals(a.getCodigo()));
        assertTrue(contieneA001, "La lista debe contener el artículo con código A001");
        assertTrue(contieneA002, "La lista debe contener el artículo con código A002");

    }

    @Test
    @Order(7)
    @DisplayName("Debe buscar un artículo por parte de su descripción.")
    void testBuscarArticuloPorDescripcion() {
        Articulo encontrado = dao.buscarArticuloPorDescripcion("prueba");
        assertNotNull(encontrado);
        assertEquals("A001", encontrado.getCodigo());
        assertTrue(encontrado.getDescripcion().contains("prueba"));

        assertThrows(
                ElementoNoEncontradoException.class,
                () -> dao.buscarArticuloPorDescripcion("inexistente")
        );
    }

    @Test
    @Order(8)
    @DisplayName("Debe buscar artículos por parte de su descripción.")
    void testBuscarPorDescripcion() {
        List<Articulo> encontrados = dao.buscarPorDescripcion("1");
        assertNotNull(encontrados);
        assertEquals(2, encontrados.size()); // En la BBDD ya existen artículos con descripciones que contienen 1
        assertEquals("A001", encontrados.getFirst().getCodigo());

        encontrados = dao.buscarPorDescripcion("artículo");
        assertNotNull(encontrados);
        assertTrue(encontrados.size() >= 2);

        boolean contieneA001 = encontrados.stream().anyMatch(a -> "A001".equals(a.getCodigo()));
        boolean contieneA002 = encontrados.stream().anyMatch(a -> "A002".equals(a.getCodigo()));
        assertTrue(contieneA001);
        assertTrue(contieneA002);

        encontrados = dao.buscarPorDescripcion("inexistente");
        assertTrue(encontrados.isEmpty(), "Debe devolver una lista vacía si no hay coincidencias.");
    }
}
