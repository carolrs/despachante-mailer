package io.carolrs.despachantemailer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.carolrs.despachantemailer.model.FormValues;
import io.carolrs.despachantemailer.model.Formulario;
import io.carolrs.despachantemailer.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin("*")
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping(produces = APPLICATION_JSON_VALUE, headers = "content-type=multipart/form-data", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void sendEmail(@ModelAttribute FormValues formValues) throws IOException {
        Formulario formulario = objectMapper.readValue(formValues.getData(), Formulario.class);
        emailService.sendEmail(formulario.getInputEmail(), formulario.getInputName(), formulario.getInputDetalhes(), formValues.getFiles());
    }
}
