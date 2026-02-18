package com.Arthur.API_Cliente.DTO;

import lombok.Data;



public record ViaCepDTO(
    String cep,
     String bairro,
     String localidade,
    String uf,
    String logradouro,
    Boolean erro
){}
