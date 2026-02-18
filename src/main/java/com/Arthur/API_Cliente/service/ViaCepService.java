package com.Arthur.API_Cliente.service;

import com.Arthur.API_Cliente.cliente.ViaCepClient;
import com.Arthur.API_Cliente.entity.Endereco;
import com.Arthur.API_Cliente.exception.CepInvalidoException;
import com.Arthur.API_Cliente.DTO.ViaCepDTO;
import com.Arthur.API_Cliente.mapper.EnderecoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ViaCepService {
    private final EnderecoMapper enderecoMapper;
    private final ViaCepClient viaCepClient;

    public Endereco buscarEnderecoPorCep(String cep,String numero) {

        ViaCepDTO viaCepDTO = viaCepClient.buscaEnderecoPor(cep);
        if (viaCepDTO.erro()!=null) {
            throw new CepInvalidoException("Cep invalido");
        }
        return enderecoMapper.ViacepDtoParaEndereco(viaCepDTO,numero);
    }



}

