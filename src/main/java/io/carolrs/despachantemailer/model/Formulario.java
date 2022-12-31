package io.carolrs.despachantemailer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Formulario {
    private String inputName;
    private String inputEmpresa;
    private String inputEmail;
    private String inputPhone;
    private String inputAddress;
    private String inputAddress2;
    private String inputCity;
    private String inputState;
    private String inputZip;
    private String inputDetalhes;
}

