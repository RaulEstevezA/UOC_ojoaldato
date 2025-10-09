package ojoaldato.modelo;

/**
 * Clase abstracta base del modelo que representa a un cliente.
 * Se completará con atributos y métodos según el UML definitivo (AA1).
 */
public abstract class Cliente {
    private String nombre;
    private String domicilio;
    private String nif;
    private String email;
    private BigDecimal cuota;

    public Cliente() {}
    public Cliente(String nombre, String domicilio, String nif, String email, BigDecimal cuota) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
        this.cuota = cuota;
    }

    public abstract BigDecimal calcularGastosEnvio(BigDecimal base);

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getCuota() {
        return cuota;
    }

    public void setCuota(BigDecimal cuota) {
        this.cuota = cuota;
    }

    @Override
    public String toString() {
        return "Cliente: \n" +
                "Nombre = " + nombre + '\n' +
                "Domicilio = " + domicilio + '\n' +
                "NIF = " + nif + '\n' +
                "Email = " + email + '\n' +
                "Cuota = " + cuota + '\n';
    }
}