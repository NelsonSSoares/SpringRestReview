package br.com.nelsonssoares.springreview.file.importer.impl;

import br.com.nelsonssoares.springreview.domain.dtos.v1.PersonDTO;
import br.com.nelsonssoares.springreview.file.importer.contract.FileImporter;

import java.io.InputStream;
import java.util.List;

public class XLSXImporter implements FileImporter {
    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {
        return List.of();
    }
}
