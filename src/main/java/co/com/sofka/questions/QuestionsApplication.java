package co.com.sofka.questions;

import co.com.sofka.questions.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.MessagingException;
import java.io.IOException;

@SpringBootApplication
public class QuestionsApplication {

    public static void main(String[] args) throws MessagingException, IOException {
    SpringApplication.run(QuestionsApplication.class, args);
    EmailService emailService = new EmailService();
    emailService.sendmail();
    }

}
