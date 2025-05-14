package br.com.nelsonssoares.springreview.controllers;

import br.com.nelsonssoares.springreview.controllers.docs.EmailControllerDocs;
import br.com.nelsonssoares.springreview.domain.dtos.request.EmailRequestDTO;
import br.com.nelsonssoares.springreview.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping
    @Override
    public ResponseEntity<String> sendEmailwithAttachment(String emailReqestJson, MultipartFile file) {
        return null;
    }
}
