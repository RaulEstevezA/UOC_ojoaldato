package Test;

import ojoaldato.exception.ElementoNoEncontradoException;
import ojoaldato.modelo.Datos;

public class TestElementoNoEncontrado {
    @Test
    void testBuscarClienteNoExistente() {
        Datos datos = new Datos();
        assertThrows(ElementoNoEncontradoException.class, () -> datos.buscarCliente("noexiste@example.com"));
    }
}
