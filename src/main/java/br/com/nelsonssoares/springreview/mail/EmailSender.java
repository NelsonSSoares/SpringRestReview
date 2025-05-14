package br.com.nelsonssoares.springreview.mail;

import br.com.nelsonssoares.springreview.config.email.EmailConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class EmailSender implements Serializable {

    Logger logger = getLogger(EmailSender.class);

    private final JavaMailSender mailSender;
    private String to;
    private String subject;
    private String body;
    private String from;
    private ArrayList<InternetAddress> recipients = new ArrayList<>();
    private File attachment;

    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public EmailSender to(String to) {
        this.recipients = getRecipients(to);
        return this;
    }

    public EmailSender withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailSender withMessage(String body) {
        this.body = body;
        return this;
    }

    public void setFrom(String from) {
        this.from = from;
    }



    public EmailSender setAttachment(String fileDir) {
        this.attachment = new File(fileDir);
        return this;
    }

    public void send(EmailConfig config) {
        // Implementar o envio de email aqui
        // Exemplo: mailSender.send(mimeMessage);

        logger.info("Enviando email para: " + recipients);
        logger.info("Assunto: " + subject);
        logger.info("Mensagem: " + body);
        logger.info("De: " + config.getFrom());
        logger.info("Para: " + recipients);
        logger.info("Anexo: " + attachment);

        if (attachment != null) {
            System.out.println("Anexo: " + attachment.getAbsolutePath());
        }


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(config.getUsername());
            helper.setTo(recipients.toArray(new InternetAddress[0]));
            helper.setSubject(subject);
            helper.setText(body, true);
            if (attachment != null) {
                helper.addAttachment(attachment.getName(), attachment);
            }

            mailSender.send(message);

            reset();

        } catch (MessagingException e) {
            throw new RuntimeException("error sending the email",e);
        }

    }
    private void reset() {
        this.to = null;
        this.subject = null;
        this.body = null;
        this.from = null;
        this.recipients = new ArrayList<>();
        this.attachment = null;
    }

    // cria um arraylist de InternetAddress com os endereços de email
    private ArrayList<InternetAddress> getRecipients(String to) {
        String toWithoutSpaces = to.replaceAll("\\s", "");
        StringTokenizer tok = new StringTokenizer(toWithoutSpaces, ";");
        ArrayList<InternetAddress> recipientsList = new ArrayList<>();
        while (tok.hasMoreElements()) {
            String email = tok.nextToken();
            try {
                recipientsList.add(new InternetAddress(tok.nextElement().toString()));
            } catch (AddressException e) {
                throw new RuntimeException(e);
            }
        }
        return recipientsList;
    }

}
