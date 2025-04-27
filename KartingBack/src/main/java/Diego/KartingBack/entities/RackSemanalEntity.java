package Diego.KartingBack.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "rack_semanal")
public class RackSemanalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha; // Fecha específica dentro del rack semanal

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "rack_semanal_id")
    private List<ComprobanteEntity> reservasDelDia;

    public RackSemanalEntity() {}

    public RackSemanalEntity(LocalDate fecha, List<ComprobanteEntity> reservasDelDia) {
        this.fecha = fecha;
        this.reservasDelDia = reservasDelDia;
    }

    // Getters y Setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<ComprobanteEntity> getReservasDelDia() {
        return reservasDelDia;
    }

    public void setReservasDelDia(List<ComprobanteEntity> reservasDelDia) {
        this.reservasDelDia = reservasDelDia;
    }
}
