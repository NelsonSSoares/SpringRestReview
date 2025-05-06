package br.com.nelsonssoares.springreview.file.importer.contract;

import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<PersonDTO> importFile(InputStream inputStream)throws Exception;



}
