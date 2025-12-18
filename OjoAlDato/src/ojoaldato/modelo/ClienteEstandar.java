package ojoaldato.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * Subclase de Cliente que representa un cliente estándar.
 */
@Entity
@DiscriminatorValue("ESTANDAR")  //El nombre que tendra en la columna tipo de Cliente
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=======================================\n");
        sb.append("CLIENTE ESTÁNDAR\n");
        sb.append("=======================================\n");
        sb.append(super.toString());
        sb.append("Tipo envío:   Normal (sin descuento)\n");
        return sb.toString();
    }
}
