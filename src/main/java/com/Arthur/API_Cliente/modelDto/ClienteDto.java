package com.Arthur.API_Cliente.modelDto;

import com.Arthur.API_Cliente.entity.Cliente;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;

@Data
@JsonPropertyOrder({"id","nome", "idade", "endereco"})
public class ClienteDto {
    private Long id;
    private String nome;
    private Integer idade;
    private EnderecoDTO endereco;
    public  ClienteDto(Cliente cliente){
this.endereco = new EnderecoDTO (cliente.getEndereco());
this.idade= setIdade(cliente.getNascimento());
this.nome= cliente.getNome();
id= cliente.getId();

    }
    public Integer setIdade(LocalDate nascimento){

        LocalDate hoje = LocalDate.now();
        return Period.between(nascimento,hoje).getYears();
    }

}
