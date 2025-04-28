package Diego.KartingBack;

import Diego.KartingBack.entities.ClienteEntity;
import Diego.KartingBack.services.TarifaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TarifaServiceTest {

    private TarifaService tarifaService;

    @BeforeEach
    public void setup() {
        tarifaService = new TarifaService();
    }

    @Test
    public void testCalcularTarifaBase() {
        assertEquals(15000.0, tarifaService.calcularTarifaBase(10));
        assertEquals(20000.0, tarifaService.calcularTarifaBase(15));
        assertEquals(25000.0, tarifaService.calcularTarifaBase(20));
        assertThrows(IllegalArgumentException.class, () -> tarifaService.calcularTarifaBase(25)); // Número de vueltas inválido
    }

    @Test
    public void testCalcularTarifaFinal_DescuentoCumpleanos() {
        ClienteEntity clienteCumpleanos = new ClienteEntity("Diego", 1, true);
        List<ClienteEntity> clientes = List.of(clienteCumpleanos);

        Double tarifaFinal = tarifaService.calcularTarifaFinal(clientes, 10);

        assertEquals(7500.0, tarifaFinal); // ✅ Se aplica el 50% de descuento
    }

    @Test
    public void testCalcularTarifaFinal_DescuentoGrupo() {
        ClienteEntity cliente1 = new ClienteEntity("Juan", 1, false);
        ClienteEntity cliente2 = new ClienteEntity("Maria", 1, false);
        ClienteEntity cliente3 = new ClienteEntity("Pedro", 1, false);
        List<ClienteEntity> clientes = List.of(cliente1, cliente2, cliente3);

        Double tarifaFinal = tarifaService.calcularTarifaFinal(clientes, 10);

        assertEquals(40500.0, tarifaFinal); // ✅ Se aplica 10% de descuento grupal
    }

    @Test
    public void testCalcularTarifaFinal_DescuentoFrecuencia() {
        ClienteEntity clienteFrecuente = new ClienteEntity("Diego", 5, false);
        List<ClienteEntity> clientes = List.of(clienteFrecuente);

        Double tarifaFinal = tarifaService.calcularTarifaFinal(clientes, 10);

        assertEquals(12000.0, tarifaFinal); // ✅ Se aplica 20% de descuento por visitas frecuentes
    }

    @Test
    public void testCalcularTarifasPorCliente() {
        ClienteEntity cliente1 = new ClienteEntity("Juan", 1, false);
        ClienteEntity cliente2 = new ClienteEntity("Maria", 7, false);
        List<ClienteEntity> clientes = List.of(cliente1, cliente2);

        List<Map<String, Object>> resultados = tarifaService.calcularTarifasPorCliente(clientes, 15);

        assertEquals(2, resultados.size());
        assertEquals("Juan", resultados.get(0).get("nombre"));
        assertEquals(20000.0, resultados.get(0).get("tarifaFinal")); // 10% de descuento por grupo
        assertEquals("Maria", resultados.get(1).get("nombre"));
        assertEquals(14000.0, resultados.get(1).get("tarifaFinal")); // 30% de descuento por frecuencia
    }
}
