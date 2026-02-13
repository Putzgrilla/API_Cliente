package com.Arthur.API_Cliente.DTO;


import com.Arthur.API_Cliente.entity.Endereco;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({"cep", "uf", "localidade", "bairro", "logradouro", "numero"})
@Data
public class EnderecoDTO {
    private String cep;
    private String bairro;
    private String localidade;
    private String uf;
    private String logradouro;
    private String numero;

    public EnderecoDTO(Endereco endereco) {
        cep = endereco.getCep();
        bairro = endereco.getBairro();
        localidade = endereco.getLocalidade();
        uf = endereco.getUf();
        logradouro = endereco.getLogradouro();
        numero = endereco.getNumero();


    }
}
