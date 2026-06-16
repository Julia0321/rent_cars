package pl.dmcs.jmazur.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(String receiver, String content, String subject) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("EmailAuthor");
        mail.setTo(receiver);
        mail.setSubject(subject);
        mail.setText(content);
        javaMailSender.send(mail);
    }
}
