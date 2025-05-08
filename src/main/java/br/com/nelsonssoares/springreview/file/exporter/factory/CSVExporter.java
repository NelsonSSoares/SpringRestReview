package br.com.nelsonssoares.springreview.file.exporter.factory;

import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;
import br.com.nelsonssoares.springreview.file.exporter.contract.FileExporter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CSVExporter implements FileExporter {

    @Override
    public Resource exportFile(List<PersonDTO> peopleDTOList) {
        return null;
    }
}
