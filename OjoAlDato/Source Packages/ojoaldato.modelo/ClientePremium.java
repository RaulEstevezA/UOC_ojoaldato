package ojoaldato.modelo;

/**
 * Subclase de Cliente que representa un cliente premium.
 */
public class ClientePremium extends Cliente {
    private static BigDecimal descuentoEnvio = 0.8;

    public ClientePremium() {}
    public ClientePremium(String nombre, String domicilio, String nif, String email, BigDecimal cuota, BigDecimal descuentoEnvio) {
        super(nombre, domicilio, nif, email, cuota);
        this.descuentoEnvio = descuentoEnvio;
    }

    @Override
    public BigDecimal calcularGastosEnvio(BigDecimal base) {
        return base.multiply(descuentoEnvio);
    }

    public static BigDecimal getDescuentoEnvio() {
        return descuentoEnvio;
    }

    public static void setDescuentoEnvio(BigDecimal descuentoEnvio) {
        ClientePremium.descuentoEnvio = descuentoEnvio;
    }
}
