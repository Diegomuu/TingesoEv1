package Diego.KartingBack.services;

import Diego.KartingBack.entities.ComprobanteEntity;
import Diego.KartingBack.entities.RackSemanalEntity;
import Diego.KartingBack.repositories.RackSemanalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;


@Service
public class RackSemanalService {

    @Autowired
    private RackSemanalRepository rackSemanalRepository;

    public void agregarReservaAlRack(ComprobanteEntity comprobante) {
        LocalDate fechaReserva = comprobante.getFechaHoraReserva().toLocalDate();

        RackSemanalEntity rack = rackSemanalRepository.findByFecha(fechaReserva)
                .orElse(new RackSemanalEntity(fechaReserva, new ArrayList<>()));

        rack.getReservasDelDia().add(comprobante);
        rackSemanalRepository.save(rack);
    }
}
