package ojoaldato.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

/**
 * Subclase de Cliente que representa un cliente premium.
 */
public class ClientePremium extends Cliente {
    private static BigDecimal cuota;
    private static BigDecimal descuentoEnvio = new BigDecimal("0.8");

    public ClientePremium() {
        this.cuota = BigDecimal.valueOf(30.00);
    }
    public ClientePremium(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
        this.cuota = BigDecimal.valueOf(30.00);
    }
    public ClientePremium(String nombre, String domicilio, String nif, String email, BigDecimal cuota, BigDecimal descuentoEnvio) {
        super(nombre, domicilio, nif, email, cuota);
        this.cuota = cuota != null ? cuota : BigDecimal.valueOf(30.00);
        if (descuentoEnvio != null) ClientePremium.descuentoEnvio = descuentoEnvio;
    }

    @Override
    public BigDecimal calcularGastosEnvio(BigDecimal base) {
        return base.multiply(descuentoEnvio).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getCuota() {
        return cuota;
    }

    @Override
    public void setCuota(BigDecimal cuota) {
        ClientePremium.cuota = cuota;
    }

    public static BigDecimal getDescuentoEnvio() {
        return descuentoEnvio;
    }

    public static void setDescuentoEnvio(BigDecimal descuentoEnvio) {
        ClientePremium.descuentoEnvio = descuentoEnvio;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=======================================\n");
        sb.append("CLIENTE PREMIUM\n");
        sb.append("=======================================\n");
        sb.append(super.toString());
        sb.append("Descuento envío:             \n")
                .append(descuentoEnvio.multiply(BigDecimal.valueOf(100)).intValue())
                .append("%\n");
        sb.append("Cuota mensual:             ")
                .append(cuota.setScale(2, RoundingMode.HALF_UP))
                .append(" €\n");
        return sb.toString();
    }
}
