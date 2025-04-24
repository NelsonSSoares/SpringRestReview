package br.com.nelsonssoares.springreview.controllers.docs;

import br.com.nelsonssoares.springreview.domain.dtos.UploadFileResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import br.com.nelsonssoares.springreview.services.FileStorageService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/file")
public class FileController implements FileControllerDocs{

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    public FileStorageService fileService;

    @Override
    public UploadFileResponseDTO uploadFile(MultipartFile file) {
        return null;
    }

    @Override
    public List<UploadFileResponseDTO> uploadMultipleFiles(MultipartFile[] file) {
        return List.of();
    }

    @Override
    public ResponseEntity<ResponseEntity> downloadFile(String fileName, HttpServletRequest request) {
        return null;
    }
}
