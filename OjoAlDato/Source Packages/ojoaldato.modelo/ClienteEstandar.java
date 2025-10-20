package ojoaldato.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * Subclase de Cliente que representa un cliente estándar.
 */
public class ClienteEstandar extends Cliente {

    @Override
    public BigDecimal calcularGastosEnvio(BigDecimal base) {
        return base.setScale(2, RoundingMode.HALF_UP);
    }
}
