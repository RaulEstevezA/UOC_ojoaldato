package Test;

import ojoaldato.exception.ElementoNoEncontradoException;
import ojoaldato.modelo.Datos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestElementoNoEncontrado {
    @Test
    void testBuscarClienteNoExistente() {
        Datos datos = new Datos();
        assertThrows(ElementoNoEncontradoException.class, () -> datos.buscarCliente("noexiste@example.com"));
    }
}
