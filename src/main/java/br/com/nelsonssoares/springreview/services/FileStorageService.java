package br.com.nelsonssoares.springreview.services;

import br.com.nelsonssoares.springreview.config.download.FileStorageConfig;
import br.com.nelsonssoares.springreview.exceptions.FileNotFoundException;
import br.com.nelsonssoares.springreview.exceptions.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        logger.info("Creating Directories for file storage");
        // cria o diretorio aonde os arquivos serao armazenados e normaliza o caminho,removendo caracteres invalidos
        Path path = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();
        this.fileStorageLocation = path;

        try{
            // cria o diretorio
            Files.createDirectories(this.fileStorageLocation);

        }catch (Exception e){
            logger.error("Could not create the directory where the uploaded files will be stored.", e);
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    public String storeFile(MultipartFile file) throws IOException {
        // Normaliza o nome do arquivo, removendo caracteres invalidos

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            logger.info("saving file in disk");
            // Verifica se o arquivo é vazio
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            // Copia o arquivo para o diretorio de armazenamento e substitui o arquivo se ja existir
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            logger.error("Could not store file " + fileName + " Please try again!", e);
            throw new FileStorageException("Could not store file " + fileName + " Please try again!", e);
        }

    }

    public Resource loadFileAsResource(String fileName) {
       try {
           Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
           Resource resource = new UrlResource(filePath.toUri());
              if (resource.exists()) {
                return resource;
              } else {
                throw new FileNotFoundException("File not found " + fileName);
              }
       } catch (Exception e) {
              logger.error("Could not load file " + fileName + " Please try again!", e);
           throw new FileNotFoundException("File not found "+ fileName ,e);
       }
    }



}
