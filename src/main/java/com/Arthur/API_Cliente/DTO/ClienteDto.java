package com.Arthur.API_Cliente.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"id", "nome", "idade", "endereco"})
public record ClienteDto(
        Long id,
        String nome,
        Integer idade,
        EnderecoDTO endereco

) {
}
