package ojoaldato.modelo;

import java.math.BigDecimal;


/**
 * Clase que representa un artículo disponible en el sistema.
 * Identidad: codigo (único).
 */
public class Articulo {
    private String codigo;
    private String descripcion;
    private BigDecimal pvp;
    private BigDecimal gastosEnvio;
    private Integer tiempoPreparacion;
    private Integer stock;
    private Boolean activo;

    public Articulo() {}
    public Articulo(String codigo, String descripcion, BigDecimal pvp, BigDecimal gastosEnvio, Integer tiempoPreparacion, Integer stock) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.pvp = pvp;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
        this.stock = stock;
        this.activo = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPvp() {
        return pvp;
    }

    public void setPvp(BigDecimal pvp) {
        this.pvp = pvp;
    }

    public BigDecimal getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(BigDecimal gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    public Integer getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(Integer tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public Integer getStock() { return stock; }

    public void setStock(Integer stock) { this.stock = stock; }

    public Boolean getActivo() { return activo; }

    public void setActivo(Boolean esActivo) { this.activo = esActivo; }

    @Override
    public String toString() {
        return "Artículo: " +
                "código = " + codigo + '\'' +
                ", descripción = " + descripcion + '\'' +
                ", PVP = " + pvp +
                ", gastos de envío = " + gastosEnvio +
                ", tiempo de preparacion (en minutos) = " + tiempoPreparacion + '\n';
    }
}
