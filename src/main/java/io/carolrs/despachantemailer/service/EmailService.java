package io.carolrs.despachantemailer.service;

import io.carolrs.despachantemailer.config.ConfigProperties;
import io.carolrs.despachantemailer.model.Formulario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final AttachmentHelper attachmentHelper;

    private final ConfigProperties properties;

    public void sendEmail(Formulario formulario, MultipartFile[] multipartFiles) {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(properties.getRecipient());
            helper.setTo(properties.getRecipient());
            helper.setSubject(formulario.getInputName());
            helper.setText(formulario.getInputDetalhes());

            Stream.of(multipartFiles)
                    .forEach(file ->
                            attachmentHelper.addAttachmentToHelper(helper, file)
                    );

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
