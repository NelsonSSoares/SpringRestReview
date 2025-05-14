package br.com.nelsonssoares.springreview.controllers.docs;

import br.com.nelsonssoares.springreview.domain.dtos.request.EmailRequestDTO;
import br.com.nelsonssoares.springreview.file.exporter.MyMediaTypes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface EmailControllerDocs {

    @Operation(summary = "Send an e-mail",
             description = "Send an e-mail by providing details, subject, and body",
             tags = {"Email"},
             responses = {
            @ApiResponse(description = "Success", responseCode = "200"),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
    })
    ResponseEntity<String> sendEmail(EmailRequestDTO emailRequestDTO);

    @Operation(summary = "Send an e-mail with Attachment",
            description = "Send an e-mail by providing details, subject, body and Attachment",
            tags = {"Email"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            })
    ResponseEntity<String> sendEmailwithAttachment(String emailReqestJson, MultipartFile file);
}
