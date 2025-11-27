package ojoaldato.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;



 // Clase JPA de Cliente
@Entity
@Table(name = "clientes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //Le decimos "que la clase va a ser una padre" y que cree una sola tabla para las subclases
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)  //Esto sirve para identificar a que subclase pertenece el cliente mediante una columna llamada tipo.
public abstract class Cliente {
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "domicilio", nullable = false)
    private String domicilio;
    @Column(name = "nif", nullable = false)
    private String nif;
    @Id
    @Column(name = "email", nullable = false)
    private String email;
    @Transient
    private BigDecimal cuota;


    // Método polifórmico
    public abstract BigDecimal calcularGastosEnvio(BigDecimal base);


    public Cliente() {}
    public Cliente(String nombre, String domicilio, String nif, String email) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
    }
    public Cliente(String nombre, String domicilio, String nif, String email, BigDecimal cuota) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
        this.cuota = cuota;
    }


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
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------\n");
        sb.append("CLIENTE\n");
        sb.append("-------------------------------------------\n");
        sb.append("Nombre:            ").append(nombre).append("\n");
        sb.append("Domicilio:         ").append(domicilio).append("\n");
        sb.append("NIF:               ").append(nif).append("\n");
        sb.append("Email:             ").append(email).append("\n");

        if (cuota != null) {
            sb.append("Cuota:             ").append(cuota.setScale(2, RoundingMode.HALF_UP));
        }

        return sb.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente cliente = (Cliente) obj;
        return email != null && email.equalsIgnoreCase(cliente.email);
    }

    @Override
    public int hashCode() {
        return email != null ? email.toLowerCase().hashCode() : 0;
    }
} 