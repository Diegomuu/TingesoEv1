package Diego.KartingBack;

import Diego.KartingBack.entities.ComprobanteEntity;

import Diego.KartingBack.entities.DetallePagoEntity;
import Diego.KartingBack.repositories.ComprobanteRepository;
import Diego.KartingBack.services.ComprobanteService;
import Diego.KartingBack.services.RackSemanalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComprobanteServiceTest {

    @InjectMocks
    private ComprobanteService comprobanteService;

    @Mock
    private ComprobanteRepository comprobanteRepository;

    @Mock
    private RackSemanalService rackSemanalService;

    @Test
    public void testGuardarComprobante_CreaComprobante() {
        // Simula una lista de pagos con todos los atributos necesarios
        List<DetallePagoEntity> detallesPago = List.of(
                new DetallePagoEntity("Juan", 100.0, 10.0, 5.0, 85.0, 16.15),
                new DetallePagoEntity("Maria", 200.0, 20.0, 10.0, 170.0, 32.3)
        );

        // Simula que `save` devuelve un comprobante guardado
        ComprobanteEntity comprobanteSimulado = new ComprobanteEntity(
                "RES123456", LocalDateTime.now(), 5, 2, "Diego",
                255.0, 48.45, detallesPago
        );
        when(comprobanteRepository.save(any(ComprobanteEntity.class))).thenReturn(comprobanteSimulado);

        // Ejecuta el método
        comprobanteService.guardarComprobante("Diego", 5, detallesPago);

        // Verifica que se haya guardado correctamente
        verify(comprobanteRepository, times(1)).save(any(ComprobanteEntity.class));
    }

    @Test
    public void testObtenerComprobantePorCodigo_Existe() {
        ComprobanteEntity comprobante = new ComprobanteEntity("RES123456", LocalDateTime.now(), 5, 2, "Diego", 300.0, 57.0, List.of());
        when(comprobanteRepository.findByCodigoReserva("RES123456")).thenReturn(Optional.of(comprobante));

        Optional<ComprobanteEntity> resultado = comprobanteService.obtenerComprobantePorCodigo("RES123456");

        assertTrue(resultado.isPresent());
        assertEquals("Diego", resultado.get().getNombreReservante());
    }
}
