package br.com.nelsonssoares.springreview.file.importer.impl;

import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;
import br.com.nelsonssoares.springreview.file.importer.contract.FileImporter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XLSXImporter implements FileImporter {

    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            var sheet = workbook.getSheetAt(0);
            var rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next(); // Skip header
            }

            return parseRowsToPersonDTOList(rowIterator);
        }
    }

    private List<PersonDTO> parseRowsToPersonDTOList(Iterator<Row> rowIterator) {
        List<PersonDTO> people = new ArrayList<>();

        while (rowIterator.hasNext()) {
            var row = rowIterator.next();

            if (isValid(row)) {
                people.add(parseRowToPersonDTO(row));
            }
        }

        return people;
    }

    private static boolean isValid(Row row) {
        return row.getCell(0) != null && row.getCell(0).getCellType() != CellType.BLANK;
    }

    private PersonDTO parseRowToPersonDTO(Row row) {
        var person = new PersonDTO();
        person.setFirstName(row.getCell(0).getStringCellValue());
        person.setLastName(row.getCell(1).getStringCellValue());
        person.setAddress(row.getCell(2).getStringCellValue());
        person.setGender(row.getCell(3).getStringCellValue());
        person.setEnabled(true);
        return person;
    }
}
