package Diego.KartingBack.controllers;

import Diego.KartingBack.entities.ClienteEntity;
import Diego.KartingBack.entities.ReservaEntity;
import Diego.KartingBack.entities.TarifaRequest;
import Diego.KartingBack.repositories.ReservaRepository;
import Diego.KartingBack.services.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservas")
@CrossOrigin
public class ReservaController {

    @Autowired
    private TarifaService tarifaService;
    private ReservaRepository reservaRepository;

    @PostMapping("/calcularTarifa")
    public ResponseEntity<List<Map<String, Object>>> calcularTarifa(@RequestBody TarifaRequest tarifaRequest) {
        List<Map<String, Object>> tarifasIndividuales = tarifaService.calcularTarifasPorCliente(tarifaRequest.getClientes(), tarifaRequest.getVueltas());
        return ResponseEntity.ok(tarifasIndividuales);
    }

    public ReservaController(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @PostMapping("/crear")
    public ResponseEntity<ReservaEntity> crearReserva(@RequestBody ReservaEntity reserva) {
        ReservaEntity nuevaReserva = reservaRepository.save(reserva);
        return ResponseEntity.ok(nuevaReserva);
    }






}
