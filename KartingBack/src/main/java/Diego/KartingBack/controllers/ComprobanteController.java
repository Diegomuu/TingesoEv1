package Diego.KartingBack.controllers;

import Diego.KartingBack.dto.ComprobanteRequest;
import Diego.KartingBack.entities.ComprobanteEntity;
import Diego.KartingBack.entities.DetallePagoEntity;
import Diego.KartingBack.services.ComprobanteService;
import Diego.KartingBack.services.ExcelService; // Importamos el servicio para generar Excel
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/comprobantes")
@CrossOrigin
public class ComprobanteController {

    private final ComprobanteService comprobanteService;
    private final ExcelService excelService; // Agregamos ExcelService

    public ComprobanteController(ComprobanteService comprobanteService, ExcelService excelService) {
        this.comprobanteService = comprobanteService;
        this.excelService = excelService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, String>> guardarComprobante(@RequestBody ComprobanteEntity comprobante) {
        try {
            comprobanteService.guardarComprobante(
                    comprobante.getNombreReservante(),
                    comprobante.getVueltasReservadas(),
                    comprobante.getDetallesPago()
            );

            Map<String, String> response = new HashMap<>();
            response.put("message", "Comprobante guardado con éxito");
            response.put("codigoReserva", comprobante.getCodigoReserva()); // ✅ Incluye el código de reserva

            return ResponseEntity.ok(response); // ✅ Devuelve un JSON en lugar de texto plano

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al guardar el comprobante: " + e.getMessage()));
        }
    }

    // ✅ Nuevo Endpoint para Generar Excel
    @GetMapping("/excel/{codigoReserva}")
    public ResponseEntity<byte[]> generarExcel(@PathVariable String codigoReserva) throws IOException {
        Optional<ComprobanteEntity> comprobante = comprobanteService.obtenerComprobantePorCodigo(codigoReserva);

        if (comprobante.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        byte[] excelBytes = excelService.generarComprobanteExcel(comprobante.get());

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=comprobante.xlsx");
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return ResponseEntity.ok().headers(headers).body(excelBytes);
    }
}

