package ojoaldato.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Clase que representa un pedido realizado por un cliente.
 */
@Entity
@Table(name ="pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Numero de pedido generado automaticamente por la base de datos con autoincremento.
    @Column(name = "num_pedido")
    private int numPedido;

    @ManyToOne(optional = false)
    @JoinColumn(name = "email_cliente", referencedColumnName = "email", nullable = false) //La columna email_cliente apunta a la clave primaria del cliente (email). optional=false indica que el pedido siempre debe tener cliente.
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_articulo", referencedColumnName = "codigo", nullable = false)
    private Articulo articulo;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "enviado", nullable = false)
    private Boolean enviado = false;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "precio_total", precision = 10, scale = 2)
    private BigDecimal precioTotal;

    @Column(name = "gastos_envio", precision = 10, scale = 2)
    private BigDecimal gastosEnvio;

    /**
     * Constructor completo
     */
    public Pedido(int numPedido, Cliente cliente, Articulo articulo, int cantidad, LocalDateTime fechaHora) {
        if (cliente == null) throw new IllegalArgumentException("El cliente no puede ser nulo");
        if (articulo == null) throw new IllegalArgumentException("El artículo no puede ser nulo");
        if (cantidad < 1) throw new IllegalArgumentException("La cantidad debe ser mayor o igual a 1");

        this.numPedido = numPedido;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHora = (fechaHora != null) ? fechaHora : LocalDateTime.now();
    }

    public Pedido() {}

    // numPedido se asignará automáticamente por la BD y el DAO
    public Pedido(Cliente cliente, Articulo articulo, int cantidad, LocalDateTime fechaHora) {
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;

    }

    // Getters y setters básicos
    public int getNumPedido() { return numPedido; }
    public void setNumPedido(int numPedido) { this.numPedido = numPedido; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public Boolean getEnviado() { return enviado; }
    public void setEnviado(Boolean enviado) { this.enviado = enviado; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public BigDecimal getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(BigDecimal precioTotal) { this.precioTotal = precioTotal; }

    public BigDecimal getGastosEnvio() { return gastosEnvio; }
    public void setGastosEnvio(BigDecimal gastosEnvio) { this.gastosEnvio = gastosEnvio; }

    /**
     * Calcula el importe total del pedido (precio * cantidad + envío)
     * usando el polimorfismo del tipo de cliente.
     */
    public BigDecimal calcularImporteTotal() {
        BigDecimal precioBase = articulo.getPvp().multiply(BigDecimal.valueOf(cantidad));
        BigDecimal envioBase = articulo.getGastosEnvio();
        BigDecimal envioFinal = cliente.calcularGastosEnvio(envioBase);

        return precioBase.add(envioFinal).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Indica si el pedido puede cancelarse (no ha pasado el tiempo de preparación).
     */
    public boolean esCancelable(LocalDateTime ahora) {
        if (ahora == null) ahora = LocalDateTime.now();
        long minutos = Duration.between(fechaHora, ahora).toMinutes();
        return minutos < articulo.getTiempoPreparacion();
    }

    @PrePersist
    protected void prePersist() {
        if (fechaHora == null) {
            fechaHora = LocalDateTime.now();
        }
        // Guardamos también los importes calculados para reflejar el estado en el momento del pedido
        this.gastosEnvio = articulo != null ? cliente.calcularGastosEnvio(articulo.getGastosEnvio()) : BigDecimal.ZERO;
        this.precioTotal = calcularImporteTotal();
        if (enviado == null) enviado = false;
    }

    @Override
    public String toString() {
        return "Pedido Nº " + numPedido + "\n" +
                "Cliente: " + cliente.getEmail() + "\n" +
                "Artículo: " + articulo.getDescripcion() + "\n" +
                "Cantidad: " + cantidad + "\n" +
                "Fecha: " + fechaHora + "\n" +
                "Importe total: " + calcularImporteTotal() + " €\n";
    }
}
