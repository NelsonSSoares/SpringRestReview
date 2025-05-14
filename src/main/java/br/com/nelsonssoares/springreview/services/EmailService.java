package br.com.nelsonssoares.springreview.services;

import br.com.nelsonssoares.springreview.config.email.EmailConfig;
import br.com.nelsonssoares.springreview.mail.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {


    private final EmailSender sender;

    private final EmailConfig config;

    public void sendSimpleEmail(String to, String subject, String body){
        sender.to(to)
        .withSubject(subject)
        .withMessage(body)
        .send(config);
    }
}
