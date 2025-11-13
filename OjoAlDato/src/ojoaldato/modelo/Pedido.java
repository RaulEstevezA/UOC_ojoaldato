package ojoaldato.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Clase que representa un pedido realizado por un cliente.
 */
public class Pedido {

    private int numPedido;
    private Cliente cliente;
    private Articulo articulo;
    private int cantidad;
    private LocalDateTime fechaHora;

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
