package com.Arthur.API_Cliente.mapper;

import com.Arthur.API_Cliente.DTO.EnderecoDTO;
import com.Arthur.API_Cliente.DTO.ViaCepDTO;
import com.Arthur.API_Cliente.entity.Endereco;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-21T21:35:36-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class EnderecoMapperImpl implements EnderecoMapper {

    @Override
    public EnderecoDTO enderecoParaDto(Endereco endereco) {
        if ( endereco == null ) {
            return null;
        }

        String cep = null;
        String bairro = null;
        String localidade = null;
        String uf = null;
        String logradouro = null;
        String numero = null;

        cep = endereco.getCep();
        bairro = endereco.getBairro();
        localidade = endereco.getLocalidade();
        uf = endereco.getUf();
        logradouro = endereco.getLogradouro();
        numero = endereco.getNumero();

        EnderecoDTO enderecoDTO = new EnderecoDTO( cep, bairro, localidade, uf, logradouro, numero );

        return enderecoDTO;
    }

    @Override
    public Endereco ViacepDtoParaEndereco(ViaCepDTO dto, String numero) {
        if ( dto == null && numero == null ) {
            return null;
        }

        Endereco endereco = new Endereco();

        if ( dto != null ) {
            endereco.setLogradouro( dto.logradouro() );
            endereco.setCep( dto.cep() );
            endereco.setBairro( dto.bairro() );
            endereco.setLocalidade( dto.localidade() );
            endereco.setUf( dto.uf() );
        }
        endereco.setNumero( numero );

        return endereco;
    }
}
