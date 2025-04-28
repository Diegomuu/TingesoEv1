package Diego.KartingBack;

import Diego.KartingBack.entities.ComprobanteEntity;
import Diego.KartingBack.entities.RackSemanalEntity;
import Diego.KartingBack.repositories.RackSemanalRepository;
import Diego.KartingBack.services.RackSemanalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RackSemanalServiceTest {

    @InjectMocks
    private RackSemanalService rackSemanalService;

    @Mock
    private RackSemanalRepository rackSemanalRepository;

    @Test
    public void testAgregarReservaAlRack_NuevoRack() {
        // Simula la reserva
        ComprobanteEntity comprobante = new ComprobanteEntity("RES123", LocalDateTime.now(), 5, 2, "Diego", 300.0, 57.0, null);
        LocalDate fechaReserva = comprobante.getFechaHoraReserva().toLocalDate();

        // Simula que el rack no existe en la base de datos
        when(rackSemanalRepository.findByFecha(fechaReserva)).thenReturn(Optional.empty());

        // Ejecuta el método
        rackSemanalService.agregarReservaAlRack(comprobante);

        // Verifica que se haya guardado en el repositorio
        verify(rackSemanalRepository, times(1)).save(any(RackSemanalEntity.class));
    }

    @Test
    public void testAgregarReservaAlRack_RackExistente() {
        // Simula la reserva
        ComprobanteEntity comprobante = new ComprobanteEntity("RES123", LocalDateTime.now(), 5, 2, "Diego", 300.0, 57.0, null);
        LocalDate fechaReserva = comprobante.getFechaHoraReserva().toLocalDate();

        // Simula un rack que ya tiene reservas
        RackSemanalEntity rackExistente = new RackSemanalEntity(fechaReserva, new ArrayList<>());
        when(rackSemanalRepository.findByFecha(fechaReserva)).thenReturn(Optional.of(rackExistente));

        // Ejecuta el método
        rackSemanalService.agregarReservaAlRack(comprobante);

        // Verifica que la reserva fue agregada
        assertTrue(rackExistente.getReservasDelDia().contains(comprobante));

        // Verifica que se haya guardado la actualización en el repositorio
        verify(rackSemanalRepository, times(1)).save(rackExistente);
    }
}
