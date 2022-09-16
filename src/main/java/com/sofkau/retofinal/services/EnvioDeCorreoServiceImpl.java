package com.sofkau.retofinal.services;

import com.sofkau.retofinal.models.DetallesDeCorreo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
public class EnvioDeCorreoServiceImpl {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     *
     * @param details (Recipient = "A quien se envia", MsgBody="cuerpo del mensaje", Subject="Tema")
     *
     */
    public void sendSimpleMail(DetallesDeCorreo details){
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setFrom(sender);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(details.getRecipient()));
            message.setSubject(details.getSubject());

            message.setContent(details.getMsgBody(), "text/html");

            javaMailSender.send(message);

        }catch (Exception e) {
            System.out.println(e);
        }
    }

    public DetallesDeCorreo TemplateFeedback(DetallesDeCorreo details){
        String body[] = details.getMsgBody().split(", ", 0);
        // split[0] = name
        // split[1] = array de acciones de mejora

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String acciones = body[1]
                .replace("[", "<li>")
                .replace("; ", "</li><li>")
                .replace("]", "</li>");

        details.setMsgBody(
                "<h1>Hola " + body[0] + ",</h1>"
                        + "Acciones de mejora: <ul>"
                        + acciones
                        + "</ul>"
                        + "<i>Este Email fue enviado por <a href='google.com'>google.com</a> a las " + dtf.format(now) + "</i>"
        );

        return details;
    }
}
