package br.com.nelsonssoares.springreview.file.exporter.contract;

import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.List;

public interface FileExporter {

    Resource exportFile(List<PersonDTO> peopleDTOList);

}
