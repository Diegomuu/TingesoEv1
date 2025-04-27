package Diego.KartingBack.services;

import Diego.KartingBack.entities.ComprobanteEntity;
import Diego.KartingBack.entities.DetallePagoEntity;
import Diego.KartingBack.repositories.ComprobanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComprobanteService {

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private RackSemanalService rackSemanalService;

    public void guardarComprobante(String nombreReservante, int vueltasReservadas, List<DetallePagoEntity> detallesPago) {
        String codigoReserva = "RES" + System.currentTimeMillis();
        int cantidadPersonas = detallesPago.size();
        Double montoTotal = detallesPago.stream().mapToDouble(DetallePagoEntity::getMontoFinal).sum();
        Double ivaTotal = montoTotal * 0.19;

        ComprobanteEntity comprobante = new ComprobanteEntity(
                codigoReserva, LocalDateTime.now(), vueltasReservadas, cantidadPersonas, nombreReservante,
                montoTotal, ivaTotal, detallesPago
        );

        comprobanteRepository.save(comprobante);
        rackSemanalService.agregarReservaAlRack(comprobante); // Agregar al rack
    }

}
