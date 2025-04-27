package Diego.KartingBack.controllers;

import Diego.KartingBack.entities.RackSemanalEntity;
import Diego.KartingBack.repositories.RackSemanalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rack")
@CrossOrigin
public class RackSemanalController {

    private final RackSemanalRepository rackSemanalRepository;

    public RackSemanalController(RackSemanalRepository rackSemanalRepository) {
        this.rackSemanalRepository = rackSemanalRepository;
    }

    @GetMapping("/semanal")
    public ResponseEntity<List<RackSemanalEntity>> obtenerRackSemanal() {
        LocalDate inicioSemana = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
        LocalDate finSemana = inicioSemana.plusDays(6);

        List<RackSemanalEntity> rackSemanal = rackSemanalRepository.findAll().stream()
                .filter(rack -> !rack.getFecha().isBefore(inicioSemana) && !rack.getFecha().isAfter(finSemana))
                .toList();

        return ResponseEntity.ok(rackSemanal);
    }

}
