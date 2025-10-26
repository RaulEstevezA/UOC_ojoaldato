import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.ClientePremium;
import ojoaldato.modelo.Datos;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BuscarMailClienteTest {

    @Test
    void BuscarMailCliente() {

        Datos datos = new Datos();

        ClienteEstandar clienteEstandar = new ClienteEstandar();
        clienteEstandar.setEmail("estandar@test.com");
        ClientePremium clientePremium = new ClientePremium();
        clientePremium.setEmail("premium@test.com");

        datos.agregarCliente(clienteEstandar);
        datos.agregarCliente(clientePremium);

        assertEquals(clienteEstandar, datos.buscarCliente("estandar@test.com"));
        assertEquals(clientePremium, datos.buscarCliente("premium@test.com"));
    }
}
