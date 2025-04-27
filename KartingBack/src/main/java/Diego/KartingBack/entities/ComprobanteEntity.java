package Diego.KartingBack.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comprobantes")
public class ComprobanteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoReserva;
    private LocalDateTime fechaHoraReserva;
    private int vueltasReservadas;
    private int cantidadPersonas;
    private String nombreReservante;
    private Double montoTotal;
    private Double ivaTotal;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comprobante_id")
    private List<DetallePagoEntity> detallesPago;

    public ComprobanteEntity() {}

    public ComprobanteEntity(String codigoReserva, LocalDateTime fechaHoraReserva, int vueltasReservadas,
                             int cantidadPersonas, String nombreReservante, Double montoTotal, Double ivaTotal,
                             List<DetallePagoEntity> detallesPago) {
        this.codigoReserva = codigoReserva;
        this.fechaHoraReserva = fechaHoraReserva;
        this.vueltasReservadas = vueltasReservadas;
        this.cantidadPersonas = cantidadPersonas;
        this.nombreReservante = nombreReservante;
        this.montoTotal = montoTotal;
        this.ivaTotal = ivaTotal;
        this.detallesPago = detallesPago;
    }

    // Getters y Setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public LocalDateTime getFechaHoraReserva() {
        return fechaHoraReserva;
    }

    public void setFechaHoraReserva(LocalDateTime fechaHoraReserva) {
        this.fechaHoraReserva = fechaHoraReserva;
    }

    public int getVueltasReservadas() {
        return vueltasReservadas;
    }

    public void setVueltasReservadas(int vueltasReservadas) {
        this.vueltasReservadas = vueltasReservadas;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public String getNombreReservante() {
        return nombreReservante;
    }

    public void setNombreReservante(String nombreReservante) {
        this.nombreReservante = nombreReservante;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Double getIvaTotal() {
        return ivaTotal;
    }

    public void setIvaTotal(Double ivaTotal) {
        this.ivaTotal = ivaTotal;
    }

    public List<DetallePagoEntity> getDetallesPago() {
        return detallesPago;
    }

    public void setDetallesPago(List<DetallePagoEntity> detallesPago) {
        this.detallesPago = detallesPago;
    }
}
