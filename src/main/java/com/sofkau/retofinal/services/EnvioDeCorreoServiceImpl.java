package com.sofkau.retofinal.services;

import com.sofkau.retofinal.models.DetallesDeCorreo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
     *  "recipient": "alzategomez.raul@gmail.com",
     * 	"msgBody": "Hola Raúl,\nEstamos probando la API que envia correos",
     * 	"subject": "Saludos"
     */
    public void sendSimpleMail(DetallesDeCorreo details){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
        }

        catch (Exception e) {
            System.out.println(e);
        }
    }
}
