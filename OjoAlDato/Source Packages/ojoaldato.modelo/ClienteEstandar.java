package ojoaldato.modelo;

/**
 * Subclase de Cliente que representa un cliente estándar.
 */
public class ClienteEstandar extends Cliente {

    @Override
    public BigDecimal calcularGastosEnvio(BigDecimal base) {
        return base;
    }
}
