package br.com.sefaz.pi.relatorio.cte.exportacao;

import br.com.sefaz.pi.relatorio.cte.dto.CteDTO;
import br.com.sefaz.pi.relatorio.cte.model.Cte;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ExcelExporter {


    public byte[] export(List<CteDTO> cteDTOS) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Pessoas");

        // Cabeçalhos das colunas
        String[] headers = {"Chave","Data Processamento"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (CteDTO cteDTO : cteDTOS) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(cteDTO.getChave());
            row.createCell(1).setCellValue(cteDTO.getDataProcessamento());
//            row.createCell(2).setCellValue(person.getEmail());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Salve o arquivo Excel
//        String path = "C:\\Users\\diegodias\\Documents\\dados\\arquivo.xlsx";
//        FileOutputStream outputStream = new FileOutputStream(path);
//            workbook.write(outputStream);
//            workbook.close();


        return outputStream.toByteArray();


    }
}
