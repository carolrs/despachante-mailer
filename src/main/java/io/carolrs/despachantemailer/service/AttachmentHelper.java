package io.carolrs.despachantemailer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class AttachmentHelper {

    public void addAttachmentToHelper(MimeMessageHelper helper, MultipartFile file) {
        try {
            helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()),
                    this.toByteArrayResource(file));
        } catch (MessagingException e) {
            log.error("Error while trying to add attachment [{}] to email",
                    file.getOriginalFilename(), e);
            throw new RuntimeException(e);
        }
    }

    private ByteArrayResource toByteArrayResource(MultipartFile file) {
        try {
            return new ByteArrayResource(file.getBytes());
        } catch (IOException e) {
            log.error("Error while trying to convert MultipartFile [{}] to ByteArrayResource",
                    file.getOriginalFilename(), e);
            throw new RuntimeException(e);
        }
    }

}
