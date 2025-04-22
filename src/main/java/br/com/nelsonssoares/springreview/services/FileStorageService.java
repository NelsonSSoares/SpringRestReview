package br.com.nelsonssoares.springreview.services;

import br.com.nelsonssoares.springreview.config.download.FileStorageConfig;
import br.com.nelsonssoares.springreview.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        // cria o diretorio aonde os arquivos serao armazenados e normaliza o caminho,removendo caracteres invalidos
        Path path = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();
        this.fileStorageLocation = path;

        try{
            // cria o diretorio
            Files.createDirectories(this.fileStorageLocation);

        }catch (Exception e){
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
            // Verifica se o arquivo é vazio
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            // Copia o arquivo para o diretorio de armazenamento e substitui o arquivo se ja existir
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            throw new FileStorageException("Could not store file " + fileName + " Please try again!", e);
        }

    }


}
