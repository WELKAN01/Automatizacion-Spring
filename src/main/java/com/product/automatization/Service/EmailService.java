package com.product.automatization.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.product.automatization.Utils.EmailUtilities;
import com.product.automatization.DTO.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String FROM_ADDRESS;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(EmailRequest emailRequest) {
        // Implementation for sending email using JavaMailSender    
        try{
            EmailUtilities.formatEmail(emailRequest.getTo());
            log.info("el email paso el formato de error");
            emailRequest.setBody(EmailUtilities.sendContextBienvenidaEmailString(emailRequest.getSubject()));
            log.info("el email esta armando el cuerpo del mensaje");
            log.info(emailRequest.getTo());
            log.info(emailRequest.getSubject());
            log.info(emailRequest.getBody());
            mailSender.send(mimeMessage -> {
                MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, false, "UTF-8");
                helper.setFrom(FROM_ADDRESS);
                helper.setTo(emailRequest.getTo());
                helper.setSubject(emailRequest.getSubject());
                helper.setText(emailRequest.getBody());
            });
        }catch(Exception e){
            log.error("Error enviando email", e);
        }
    }
}
