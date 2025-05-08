package br.com.nelsonssoares.springreview.file.exporter.impl;

import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;
import br.com.nelsonssoares.springreview.file.exporter.contract.FileExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CSVExporter implements FileExporter {

    @Override
    public Resource exportFile(List<PersonDTO> peopleDTOList) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader("ID", "First Name", "Last Name", "Address", "Gender", "Enabled")
                .setSkipHeaderRecord(false)
                .build();

        try(CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)) {

            for(PersonDTO dto : peopleDTOList) {
                csvPrinter.printRecord(
                        dto.getId(),
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getAddress(),
                        dto.getGender(),
                        dto.getEnabled()
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ByteArrayResource(outputStream.toByteArray());
    }
}
