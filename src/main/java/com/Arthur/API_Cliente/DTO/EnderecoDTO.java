package com.Arthur.API_Cliente.DTO;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"cep", "uf", "localidade", "bairro", "logradouro", "numero"})

public record EnderecoDTO(
        String cep,
        String bairro,
        String localidade,
        String uf,
        String logradouro,
        String numero

) {
}
