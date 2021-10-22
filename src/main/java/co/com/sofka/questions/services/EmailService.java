package co.com.sofka.questions.services;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailService {
    private Session createSession(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("questionsandanswers.andres.sofka@gmail.com", "Sofka9006208326");
            }
        });
    }
    public void sendmail(String email, String question){
        Session session = createSession();

        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress("questionsandanswers.andres.sofka@gmail.com", false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            msg.setSubject("Notificacion de pregunta respondida - QuestionAndAnswers");
            msg.setContent("Hola usuario.\r\nEste correo es para informarle que se ha " +
                            "agregado una nueva respuesta a su pregunta " + question + "\r\nVisite nuestro sitio para verla"
                    , "text/html");
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
