package Diego.KartingBack.services;

import Diego.KartingBack.entities.ComprobanteEntity;
import Diego.KartingBack.entities.DetallePagoEntity;
import Diego.KartingBack.repositories.ComprobanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComprobanteService {

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private RackSemanalService rackSemanalService;

    public void guardarComprobante(String nombreReservante, int vueltasReservadas, List<DetallePagoEntity> detallesPago) {
        try {
            if (detallesPago == null || detallesPago.isEmpty()) {
                throw new IllegalArgumentException("Lista de detallesPago está vacía o nula.");
            }

            String codigoReserva = "RES" + System.currentTimeMillis();
            int cantidadPersonas = detallesPago.size();
            Double montoTotal = detallesPago.stream().mapToDouble(DetallePagoEntity::getMontoFinal).sum();
            Double ivaTotal = montoTotal * 0.19;

            ComprobanteEntity comprobante = new ComprobanteEntity(
                    codigoReserva, LocalDateTime.now(), vueltasReservadas, cantidadPersonas, nombreReservante,
                    montoTotal, ivaTotal, detallesPago
            );

            System.out.println("Guardando comprobante: " + comprobante); // ✅ Verificación en consola

            comprobanteRepository.save(comprobante);

            if (rackSemanalService != null) {
                rackSemanalService.agregarReservaAlRack(comprobante); // ✅ Verificación de servicio antes de llamar
            } else {
                System.err.println("rackSemanalService es nulo, no se puede agregar la reserva al rack.");
            }

        } catch (Exception e) {
            System.err.println("Error al guardar el comprobante: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Optional<ComprobanteEntity> obtenerComprobantePorCodigo(String codigoReserva) {
        return comprobanteRepository.findByCodigoReserva(codigoReserva);
    }


}
