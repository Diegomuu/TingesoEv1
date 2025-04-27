package Diego.KartingBack.controllers;

import Diego.KartingBack.dto.ComprobanteRequest;
import Diego.KartingBack.entities.ComprobanteEntity;
import Diego.KartingBack.entities.DetallePagoEntity;
import Diego.KartingBack.services.ComprobanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comprobantes")
@CrossOrigin
public class ComprobanteController {

    private final ComprobanteService comprobanteService;

    public ComprobanteController(ComprobanteService comprobanteService) {
        this.comprobanteService = comprobanteService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarComprobante(@RequestBody ComprobanteRequest comprobanteRequest) {
        // Convertimos cada DetallePagoRequest en DetallePagoEntity
        List<DetallePagoEntity> detallesPago = comprobanteRequest.getDetallesPago().stream().map(detalleRequest ->
                new DetallePagoEntity(
                        detalleRequest.getNombreCliente(),
                        detalleRequest.getTarifaBase(),
                        detalleRequest.getDescuentoGrupo(),
                        detalleRequest.getDescuentoEspecial(),
                        detalleRequest.getMontoFinal(),
                        detalleRequest.getIva()
                )
        ).toList();

        comprobanteService.guardarComprobante(
                comprobanteRequest.getNombreReservante(),
                comprobanteRequest.getVueltasReservadas(),
                detallesPago
        );

        return ResponseEntity.ok("Comprobante guardado correctamente.");
    }

}

