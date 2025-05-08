package br.com.nelsonssoares.springreview.file.exporter.factory;

import br.com.nelsonssoares.springreview.file.exporter.contract.FileExporter;
import br.com.nelsonssoares.springreview.file.exporter.impl.XLSXExporter;
import br.com.nelsonssoares.springreview.file.importer.contract.FileImporter;
import br.com.nelsonssoares.springreview.file.importer.impl.CSVImporter;
import br.com.nelsonssoares.springreview.file.importer.impl.XLSXImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static br.com.nelsonssoares.springreview.file.exporter.MyMediaTypes.APPLICATION_CSV;
import static br.com.nelsonssoares.springreview.file.exporter.MyMediaTypes.APPLICATION_XLSX;

@Component
public class FileExporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);


    // ApplicationContext é uma interface do Spring que fornece acesso ao contexto da aplicação.
    // O ApplicationContext é responsável por gerenciar os beans da aplicação, incluindo a injeção de dependências.

    @Autowired
    private ApplicationContext context;

    public FileExporter getImporter(String acceptHeader) throws Exception{


        logger.info("File name: {}", acceptHeader);
        if(acceptHeader.equalsIgnoreCase(APPLICATION_XLSX)){
            return context.getBean(XLSXExporter.class);
        }else if(acceptHeader.equalsIgnoreCase(APPLICATION_CSV)){
            return context.getBean(CSVExporter.class);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File type not supported");
        }
    }


}
