package br.com.nelsonssoares.springreview.file.exporter.impl;

import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;
import br.com.nelsonssoares.springreview.file.exporter.contract.FileExporter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class XLSXExporter implements FileExporter {


    @Override
    public Resource exportFile(List<PersonDTO> peopleDTOList) {

        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("People");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "First Name", "Last Name", "Address", "Gender", "Enabled"};

            // Criar o cabeçalho da planilha
            for (int i = 0; i < headers.length; i++) {
              Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }

            int rowIndex = 1;
            // Preencher os dados
            for (PersonDTO dto : peopleDTOList) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(dto.getId());
                row.createCell(1).setCellValue(dto.getFirstName());
                row.createCell(2).setCellValue(dto.getLastName());
                row.createCell(3).setCellValue(dto.getAddress());
                row.createCell(4).setCellValue(dto.getGender());
                row.createCell(5).setCellValue(dto.getEnabled() != null && dto.getEnabled() ? "Yes" : "No");
            }
            // Redimensionar as colunas para caber o conteúdo
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return new ByteArrayResource(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}
