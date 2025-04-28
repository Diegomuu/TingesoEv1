package Diego.KartingBack;

import Diego.KartingBack.entities.ComprobanteEntity;
import Diego.KartingBack.services.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class ExcelServiceTest {

    private final ExcelService excelService = new ExcelService();

    @Test
    public void testGenerarComprobanteExcel() throws IOException {
        // Simula un comprobante
        ComprobanteEntity comprobante = new ComprobanteEntity(
                "RES123456", LocalDateTime.now(), 5, 2, "Diego", 300.0, 57.0, null
        );

        // Ejecuta el método
        byte[] excelBytes = excelService.generarComprobanteExcel(comprobante);

        // Verifica que los bytes generados no sean nulos ni vacíos
        assertNotNull(excelBytes);
        assertTrue(excelBytes.length > 0);

        // **Leer el contenido del Excel**
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelBytes))) {
            Sheet sheet = workbook.getSheet("Comprobante");
            assertNotNull(sheet);

            // Verificar encabezados
            Row headerRow = sheet.getRow(0);
            assertEquals("Código Reserva", headerRow.getCell(0).getStringCellValue());
            assertEquals("Fecha", headerRow.getCell(1).getStringCellValue());
            assertEquals("Reservante", headerRow.getCell(2).getStringCellValue());

            // Verificar datos del comprobante
            Row dataRow = sheet.getRow(1);
            assertEquals("RES123456", dataRow.getCell(0).getStringCellValue());
            assertEquals("Diego", dataRow.getCell(2).getStringCellValue());
            assertEquals(5, (int) dataRow.getCell(3).getNumericCellValue());
            assertEquals(2, (int) dataRow.getCell(4).getNumericCellValue());
            assertEquals(300.0, dataRow.getCell(5).getNumericCellValue());
            assertEquals(57.0, dataRow.getCell(6).getNumericCellValue());
        }
    }
}
