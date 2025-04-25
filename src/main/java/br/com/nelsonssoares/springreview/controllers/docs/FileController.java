package br.com.nelsonssoares.springreview.controllers.docs;

import br.com.nelsonssoares.springreview.domain.dtos.UploadFileResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import br.com.nelsonssoares.springreview.services.FileStorageService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/file/v1")
public class FileController implements FileControllerDocs{

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    public FileStorageService fileService;

    @PostMapping("/uploadFile")
    @Override
    public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        var fileName = fileService.storeFile(file);
        // cria a URI para download do arquivo
        // o caminho do arquivo é o mesmo do upload, mas com o nome do arquivo
        // fromCurrentContextPath() retorna o caminho atual do contexto da aplicação por exemplo http://localhost:8080
        var fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/v1/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponseDTO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    @Override
    public List<UploadFileResponseDTO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> {
                    try {
                        return uploadFile(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

    }

    @Override
    public ResponseEntity<ResponseEntity> downloadFile(String fileName, HttpServletRequest request) {
        return null;
    }
}
