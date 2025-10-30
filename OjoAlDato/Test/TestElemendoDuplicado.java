package Test;

import ojoaldato.exception.ElementoDuplicadoException;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.Datos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestElemendoDuplicado {

    @Test
    void testAgregarClienteDuplicado() {
        Datos datos = new Datos();

        Cliente cliente1 = new ClienteEstandar("Juan", "Calle Luna 10", "12345678A", "juan@example.com");
        Cliente cliente2 = new ClienteEstandar("Pedro", "Calle Sol 15", "12345678A", "pedro@example.com");

        datos.agregarCliente(cliente1);

        assertThrows(ElementoDuplicadoException.class, () -> datos.agregarCliente(cliente2));
    }
}
