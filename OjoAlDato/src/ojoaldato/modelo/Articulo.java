package ojoaldato.modelo;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "articulos")
public class Articulo {

    @Id
    @Column(name = "codigo", nullable = false, length = 50)
    private String codigo;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "pvp", nullable = false)
    private BigDecimal pvp;

    @Column(name = "gastos_envio", nullable = false)
    private BigDecimal gastosEnvio;

    @Column(name = "tiempo_preparacion", nullable = false)
    private Integer tiempoPreparacion;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    public Articulo() {}

    public Articulo(String codigo, String descripcion, BigDecimal pvp,
                    BigDecimal gastosEnvio, Integer tiempoPreparacion, Integer stock) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.pvp = pvp;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
        this.stock = stock;
        this.activo = true;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPvp() { return pvp; }
    public void setPvp(BigDecimal pvp) { this.pvp = pvp; }

    public BigDecimal getGastosEnvio() { return gastosEnvio; }
    public void setGastosEnvio(BigDecimal gastosEnvio) { this.gastosEnvio = gastosEnvio; }

    public Integer getTiempoPreparacion() { return tiempoPreparacion; }
    public void setTiempoPreparacion(Integer tiempoPreparacion) { this.tiempoPreparacion = tiempoPreparacion; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return "Artículo: " +
                "código = " + codigo +
                ", descripción = " + descripcion +
                ", PVP = " + pvp +
                ", gastos de envío = " + gastosEnvio +
                ", tiempo preparación = " + tiempoPreparacion + '\n';
    }
}
