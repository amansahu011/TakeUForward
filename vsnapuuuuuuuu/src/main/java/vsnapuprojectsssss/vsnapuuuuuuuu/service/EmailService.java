package vsnapuprojectsssss.vsnapuuuuuuuu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender MailSender;

    public void sendNotificationEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sahuaman399@gmail.com"); // Your Gmail ID
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        MailSender.send(message);
    }
}
