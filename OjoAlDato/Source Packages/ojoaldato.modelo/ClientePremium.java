package ojoaldato.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Subclase de Cliente que representa un cliente premium.
 */
public class ClientePremium extends Cliente {
    private static BigDecimal descuentoEnvio = new BigDecimal("0.8");

    public ClientePremium() {}
    public ClientePremium(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }
    public ClientePremium(String nombre, String domicilio, String nif, String email, BigDecimal cuota, BigDecimal descuentoEnvio) {
        super(nombre, domicilio, nif, email, cuota);
    }

    @Override
    public BigDecimal calcularGastosEnvio(BigDecimal base) {
        return base.multiply(descuentoEnvio).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal getDescuentoEnvio() {
        return descuentoEnvio;
    }

    public static void setDescuentoEnvio(BigDecimal descuentoEnvio) {
        ClientePremium.descuentoEnvio = descuentoEnvio;
    }
}
