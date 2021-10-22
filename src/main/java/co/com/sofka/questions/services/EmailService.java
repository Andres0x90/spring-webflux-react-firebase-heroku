package co.com.sofka.questions.services;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailService {
    public void sendmail() throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("questionsandanswers.andres.sofka@gmail.com", "Sofka9006208326");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("questionsandanswers.andres.sofka@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("andrescaza246@gmail.com,andrescaza246@hotmail.com"));
        msg.setSubject("Notificacion de pregunta respuesta - QuestionAndAnswers");
        msg.setContent("Hola usuario.\nEste correo es para informarle que se ha agregado una nueva respuesta a su pregunta", "text/html");
        msg.setSentDate(new Date());

        Transport.send(msg);
    }
}
