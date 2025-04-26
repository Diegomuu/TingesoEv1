package Diego.KartingBack.controllers;

import Diego.KartingBack.entities.ClienteEntity;
import Diego.KartingBack.services.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private TarifaService tarifaService;

    @PostMapping("/calcularTarifa")
    public ResponseEntity<List<Map<String, Object>>> calcularTarifa(@RequestBody List<ClienteEntity> clientes, @RequestParam int vueltas) {
        List<Map<String, Object>> tarifasIndividuales = tarifaService.calcularTarifasPorCliente(clientes, vueltas);
        return ResponseEntity.ok(tarifasIndividuales);
    }



}
