package io.carolrs.despachantemailer.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FormValues {
    private String data;
    private MultipartFile[] files;
}
