package com.Arthur.API_Cliente.DTO;

import lombok.Data;


@Data
public class ViaCepDTO {
    private String cep;
    private String bairro;
    private String localidade;
    private String uf;
    private String logradouro;
    private boolean erro = false;
}
