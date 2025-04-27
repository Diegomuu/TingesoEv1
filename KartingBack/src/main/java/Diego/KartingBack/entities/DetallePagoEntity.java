package Diego.KartingBack.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "detalles_pago")
public class DetallePagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCliente;
    private Double tarifaBase;
    private Double descuentoGrupo;
    private Double descuentoEspecial;
    private Double montoFinal;
    private Double iva;

    public DetallePagoEntity() {}

    public DetallePagoEntity(String nombreCliente, Double tarifaBase, Double descuentoGrupo,
                             Double descuentoEspecial, Double montoFinal, Double iva) {
        this.nombreCliente = nombreCliente;
        this.tarifaBase = tarifaBase;
        this.descuentoGrupo = descuentoGrupo;
        this.descuentoEspecial = descuentoEspecial;
        this.montoFinal = montoFinal;
        this.iva = iva;
    }

    // Getters y Setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(Double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public Double getDescuentoGrupo() {
        return descuentoGrupo;
    }

    public void setDescuentoGrupo(Double descuentoGrupo) {
        this.descuentoGrupo = descuentoGrupo;
    }

    public Double getDescuentoEspecial() {
        return descuentoEspecial;
    }

    public void setDescuentoEspecial(Double descuentoEspecial) {
        this.descuentoEspecial = descuentoEspecial;
    }

    public Double getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(Double montoFinal) {
        this.montoFinal = montoFinal;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }
}
