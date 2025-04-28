package Diego.KartingBack;

import Diego.KartingBack.entities.ClienteEntity;
import Diego.KartingBack.repositories.ClienteRepository;
import Diego.KartingBack.services.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    public void testBuscarORegistrarCliente_NuevoCliente() {
        // Simula que el cliente no existe en la base de datos
        Mockito.when(clienteRepository.findByNombre("Diego")).thenReturn(Optional.empty());

        // Simula la respuesta del método save()
        ClienteEntity nuevoCliente = new ClienteEntity("Diego", 1, false);
        Mockito.when(clienteRepository.save(Mockito.any(ClienteEntity.class))).thenReturn(nuevoCliente);

        // Ejecuta el método
        ClienteEntity resultado = clienteService.buscarORegistrarCliente("Diego", false);

        // Verifica que se creó correctamente
        assertNotNull(resultado);
        assertEquals("Diego", resultado.getNombre());
        assertEquals(1, resultado.getVisitasMensuales());
        assertFalse(resultado.isCumpleanos());

        // Verifica que se haya guardado en el repositorio
        Mockito.verify(clienteRepository).save(Mockito.any(ClienteEntity.class));
    }

    @Test
    public void testBuscarORegistrarCliente_ClienteExistente() {
        // Simula que el cliente ya existe en la base de datos
        ClienteEntity clienteExistente = new ClienteEntity("Diego", 2, false);
        Mockito.when(clienteRepository.findByNombre("Diego")).thenReturn(Optional.of(clienteExistente));
        Mockito.when(clienteRepository.save(clienteExistente)).thenReturn(clienteExistente);

        // Ejecuta el método
        ClienteEntity clienteActualizado = clienteService.buscarORegistrarCliente("Diego", true);

        // Verifica que se actualizó correctamente
        assertNotNull(clienteActualizado);
        assertEquals("Diego", clienteActualizado.getNombre());
        assertEquals(3, clienteActualizado.getVisitasMensuales()); // Se incrementaron las visitas
        assertTrue(clienteActualizado.isCumpleanos()); // Se actualizó el cumpleaños

        // Verifica que se haya guardado la actualización
        Mockito.verify(clienteRepository).save(clienteExistente);
    }
}
