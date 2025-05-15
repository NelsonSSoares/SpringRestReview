package br.com.nelsonssoares.springreview.controllers;

import br.com.nelsonssoares.springreview.controllers.docs.EmailControllerDocs;
import br.com.nelsonssoares.springreview.domain.dtos.request.EmailRequestDTO;
import br.com.nelsonssoares.springreview.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/email/v1")
@RequiredArgsConstructor
public class EmailController implements EmailControllerDocs {

    private final EmailService emailService;

    @PostMapping
    @Override
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDTO emailRequest) {
        emailService.sendSimpleEmail(emailRequest);
        return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/withAttachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<String> sendEmailwithAttachment(@RequestParam("emailRequest") String emailRequest,
                                                          @RequestParam("file") MultipartFile file)
    {
        emailService.sendEmailWithAttachment(emailRequest, file);
        return new ResponseEntity<>("Email with attachment sent successfully", HttpStatus.OK);

    }
}
