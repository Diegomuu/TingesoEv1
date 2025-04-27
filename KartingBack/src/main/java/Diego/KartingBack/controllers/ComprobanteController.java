package Diego.KartingBack.controllers;

import Diego.KartingBack.dto.ComprobanteRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comprobantes")
@CrossOrigin
public class ComprobanteController {

    @PostMapping("/crear")
    public ResponseEntity<String> crearComprobante(@RequestBody ComprobanteRequest request) {
        // Lógica para guardar el comprobante en la base de datos
        System.out.println("Datos del comprobante recibidos: " + request);
        return ResponseEntity.ok("Comprobante guardado con éxito");
    }
}
