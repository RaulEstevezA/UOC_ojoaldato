package ojoaldato.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * Subclase de Cliente que representa un cliente estándar.
 */
public class ClienteEstandar extends Cliente {

    public ClienteEstandar() {}
    public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }
    public ClienteEstandar(String nombre, String domicilio, String nif, String email, BigDecimal cuota, BigDecimal descuentoEnvio) {
        super(nombre, domicilio, nif, email, cuota);
    }

    @Override
    public BigDecimal calcularGastosEnvio(BigDecimal base) {
        return base.setScale(2, RoundingMode.HALF_UP);
    }
}
