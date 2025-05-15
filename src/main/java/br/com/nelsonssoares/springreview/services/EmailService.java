package br.com.nelsonssoares.springreview.services;

import br.com.nelsonssoares.springreview.config.email.EmailConfig;
import br.com.nelsonssoares.springreview.domain.dtos.request.EmailRequestDTO;
import br.com.nelsonssoares.springreview.mail.EmailSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailService {


    private final EmailSender sender;

    private final EmailConfig config;

    public void sendSimpleEmail(EmailRequestDTO emailRequest) {
        sender.to(emailRequest.getTo())
        .withSubject(emailRequest.getSubject())
        .withMessage(emailRequest.getBody())
        .send(config);
    }

    // Este método é responsável por enviar um e-mail com um anexo.
    // Ele recebe uma string JSON representando o pedido de e-mail e um arquivo MultipartFile.
    // O JSON é convertido em um objeto EmailRequestDTO, e o arquivo é salvo temporariamente
    // antes de ser anexado ao e-mail.
    // O método lida com exceções de processamento JSON e IO, garantindo que o arquivo temporário
    // seja excluído após o envio do e-mail.
    public void sendEmailWithAttachment(String emailRequestJson, MultipartFile file) {

        File tempFile = null;
        try {
            EmailRequestDTO emailRequest = new ObjectMapper().readValue(emailRequestJson, EmailRequestDTO.class);
            tempFile = File.createTempFile("attachment", file.getOriginalFilename());
            file.transferTo(tempFile);

            sender.to(emailRequest.getTo())
                    .withSubject(emailRequest.getSubject())
                    .withMessage(emailRequest.getBody())
                    .setAttachment(tempFile.getAbsolutePath())
                    .send(config);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing email request",e);
        } catch (IOException e) {
            throw new RuntimeException("Error processing the attachment",e);
        }finally {
            if(tempFile != null && tempFile.exists()) tempFile.delete();
        }

    }
}
