package br.com.nelsonssoares.springreview.services;

import br.com.nelsonssoares.springreview.config.email.EmailConfig;
import br.com.nelsonssoares.springreview.domain.dtos.request.EmailRequestDTO;
import br.com.nelsonssoares.springreview.mail.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
