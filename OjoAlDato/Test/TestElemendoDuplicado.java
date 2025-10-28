package Test;

import ojoaldato.exception.ElementoDuplicadoException;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.Datos;

public class TestElemendoDuplicado {
    @Test
    void testAgregarClienteDuplicado() {
        Datos datos = new Datos();
        Cliente cliente1 = new Cliente("12345678A", "Juan", "juan@example.com");
        Cliente cliente2 = new Cliente("12345678A", "Pedro", "pedro@example.com");

        datos.agregarCliente(cliente1);
        assertThrows(ElementoDuplicadoException.class, () -> datos.agregarCliente(cliente2));
    }
}
