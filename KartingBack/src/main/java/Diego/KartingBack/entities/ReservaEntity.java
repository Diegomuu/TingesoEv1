package Diego.KartingBack.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "reservas")
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fechaReserva; // Fecha de la reserva

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reserva_id")
    private List<ClienteEntity> clientes; // Lista de clientes asociados a la reserva

    private int cantidadPersonas; // Número total de personas en la reserva

    public ReservaEntity() {}

    public ReservaEntity(String fechaReserva, List<ClienteEntity> clientes, int cantidadPersonas) {
        this.fechaReserva = fechaReserva;
        this.clientes = clientes;
        this.cantidadPersonas = cantidadPersonas;
    }

    // Getters y Setters
    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public List<ClienteEntity> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteEntity> clientes) {
        this.clientes = clientes;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }
}
