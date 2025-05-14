package br.com.nelsonssoares.springreview.mail;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Component
public class EmailSender implements Serializable {

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

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setRecipients(ArrayList<InternetAddress> recipients) {
        this.recipients = recipients;
    }

    public void setAttachment(File attachment) {
        this.attachment = attachment;
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
