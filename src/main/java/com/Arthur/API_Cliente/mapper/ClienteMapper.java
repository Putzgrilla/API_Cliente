package com.Arthur.API_Cliente.mapper;

import com.Arthur.API_Cliente.DTO.ClienteDto;
import com.Arthur.API_Cliente.DTO.ClienteSalvarDTO;
import com.Arthur.API_Cliente.entity.Cliente;
import com.Arthur.API_Cliente.entity.Endereco;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper( componentModel = "spring",uses = EnderecoMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ClienteMapper {
    @Mapping(target = "idade", expression = "java(calcularIdade(cliente.getNascimento()))")
    ClienteDto clienteParaDto(Cliente cliente);

    Cliente clienteSalvarParaCliente(ClienteSalvarDTO dto);

    default Integer calcularIdade(java.time.LocalDate nascimento) {
        if (nascimento == null) return null;
        return java.time.Period.between(nascimento, java.time.LocalDate.now()).getYears();
    }
}