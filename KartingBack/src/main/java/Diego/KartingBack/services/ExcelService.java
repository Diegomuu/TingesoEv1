package Diego.KartingBack.services;

import Diego.KartingBack.entities.ComprobanteEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Service
public class ExcelService {



    public byte[] generarComprobanteExcel(ComprobanteEntity comprobante) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Comprobante");

            // Crear fila de encabezados
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Código Reserva", "Fecha", "Reservante", "Vueltas", "Personas", "Monto Total", "IVA"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // **Crear fila con datos del comprobante**
            Row dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue(comprobante.getCodigoReserva() != null ? comprobante.getCodigoReserva() : "N/A");
            dataRow.createCell(1).setCellValue(comprobante.getFechaHoraReserva() != null ? comprobante.getFechaHoraReserva().toString() : "N/A");
            dataRow.createCell(2).setCellValue(comprobante.getNombreReservante() != null ? comprobante.getNombreReservante() : "N/A");
            dataRow.createCell(3).setCellValue(comprobante.getVueltasReservadas());
            dataRow.createCell(4).setCellValue(comprobante.getCantidadPersonas());
            dataRow.createCell(5).setCellValue(comprobante.getMontoTotal());
            dataRow.createCell(6).setCellValue(comprobante.getIvaTotal());

            // **Ajustar ancho de columnas para mejor visualización**
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
}
