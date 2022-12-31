package io.carolrs.despachantemailer.service;

import io.carolrs.despachantemailer.config.ConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private final ConfigProperties properties;

    public void sendEmail(String sender, String subject, String body, MultipartFile[] multipartFiles) {

        BodyPart messageBodyPart = new MimeBodyPart();
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            messageBodyPart.setText(body);

            helper.setFrom(properties.getRecipient());
            helper.setTo(properties.getRecipient());
            helper.setSubject(subject);
            helper.setText(body);

            Stream.of(multipartFiles)
                    .forEach(multipartFile -> {
                        try {
                            ByteArrayResource attachment = new ByteArrayResource(multipartFile.getBytes());
                            helper.addAttachment(Objects.requireNonNull(multipartFile.getOriginalFilename()), attachment);
                        } catch (MessagingException | IOException e) {
                            log.error("Error while trying to send email from {}", sender, e);
                            throw new RuntimeException(e);
                        }
                    });

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
