import ojoaldato.modelo.ClientePremium;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class GastosEnvioTest {

    @Test
    void testDescuentoGastosEnvio() {
        ClientePremium clientePremium = new ClientePremium(); // Creación de cliente premium para el test
        BigDecimal importeEnvioTotal = new BigDecimal("5.00"); // Creación del importe base de los gastos de envío

        BigDecimal envioPremium = clientePremium.calcularGastosEnvio(importeEnvioTotal); // Cálculo del 20% de descuento sobre el importe base del envío

        BigDecimal esperado = new BigDecimal("4.00"); //Definición del resultado esperado
        assertEquals(esperado, envioPremium); // Comparación de resultados para superar el test
    }
}