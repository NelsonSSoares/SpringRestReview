package br.com.nelsonssoares.springreview.controllers.docs;

import br.com.nelsonssoares.springreview.domain.dtos.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "File Endpoint")
public interface FileControllerDocs {

    UploadFileResponseDTO uploadFile(MultipartFile file);

    List<UploadFileResponseDTO> uploadMultipleFiles(MultipartFile[] file);

    ResponseEntity<ResponseEntity> downloadFile(String fileName, HttpServletRequest request);

}
