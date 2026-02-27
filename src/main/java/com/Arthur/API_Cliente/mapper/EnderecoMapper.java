package com.Arthur.API_Cliente.mapper;

import com.Arthur.API_Cliente.DTO.EnderecoDTO;
import com.Arthur.API_Cliente.DTO.ViaCepDTO;
import com.Arthur.API_Cliente.entity.Endereco;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EnderecoMapper {

   EnderecoDTO enderecoParaDto(Endereco endereco);

  @Mapping(target ="numero",source = "numero")
   Endereco ViacepDtoParaEndereco(ViaCepDTO dto,String numero);
}
