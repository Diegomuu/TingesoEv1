package Diego.KartingBack;

import Diego.KartingBack.entities.KartEntity;
import Diego.KartingBack.repositories.KartRepository;
import Diego.KartingBack.services.KartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KartServiceTest {

    @InjectMocks
    private KartService kartService;

    @Mock
    private KartRepository kartRepository;

    @Test
    public void testAgregarKart() {
        // Simula un kart a agregar
        KartEntity kart = new KartEntity(null, "Disponible", "SpeedKart");

        // Simula la respuesta del repositorio cuando se guarda el kart
        when(kartRepository.save(any(KartEntity.class))).thenReturn(kart);

        // Ejecuta el método
        KartEntity resultado = kartService.agregarKart(kart);

        // Verifica que el kart se guardó correctamente
        assertNotNull(resultado);
        assertEquals("Disponible", resultado.getEstado());
        assertEquals("SpeedKart", resultado.getModelo());

        // Verifica que `save()` se llamó correctamente
        verify(kartRepository, times(1)).save(any(KartEntity.class));
    }

    @Test
    public void testObtenerTodos() {
        // Simula una lista de karts almacenados
        List<KartEntity> listaKarts = List.of(
                new KartEntity(1L, "Disponible", "SpeedKart"),
                new KartEntity(2L, "En reparación", "TurboKart")
        );
        when(kartRepository.findAll()).thenReturn(listaKarts);

        // Ejecuta el método
        List<KartEntity> resultado = kartService.obtenerTodos();

        // Verifica que la lista contiene los karts esperados
        assertEquals(2, resultado.size());
        assertEquals("SpeedKart", resultado.get(0).getModelo());
        assertEquals("En reparación", resultado.get(1).getEstado());

        // Verifica que `findAll()` se llamó correctamente
        verify(kartRepository, times(1)).findAll();
    }
}
