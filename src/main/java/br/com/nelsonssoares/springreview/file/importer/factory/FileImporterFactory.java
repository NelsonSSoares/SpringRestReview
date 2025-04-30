package br.com.nelsonssoares.springreview.file.importer.factory;

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

@Component
public class FileImporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);


    // ApplicationContext é uma interface do Spring que fornece acesso ao contexto da aplicação.
    // O ApplicationContext é responsável por gerenciar os beans da aplicação, incluindo a injeção de dependências.

    @Autowired
    private ApplicationContext context;

    public FileImporter getImporter(String fileName) throws Exception{

        logger.info("File name: {}", fileName);
        if(fileName.endsWith(".xlsx")){
            return context.getBean(XLSXImporter.class);
        }else if(fileName.endsWith(".csv")){
            return context.getBean(CSVImporter.class);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File type not supported");
        }
    }

//        if(fileName.endsWith(".xlsx")){
//            return new XLSXImporter();
//        }else if(fileName.endsWith(".csv")){
//            return new CSVImporter();
//        }else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File type not supported");
//        }
//    }

}
