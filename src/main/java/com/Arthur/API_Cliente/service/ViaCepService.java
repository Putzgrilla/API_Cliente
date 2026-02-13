package com.Arthur.API_Cliente.service;

import com.Arthur.API_Cliente.cliente.ViaCepClient;
import com.Arthur.API_Cliente.entity.Endereco;
import com.Arthur.API_Cliente.exception.CepInvalidoException;
import com.Arthur.API_Cliente.DTO.ViaCepDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ViaCepService {

    private final ViaCepClient viaCepClient;

    public Endereco buscarEnderecoPorCep(String cep) {

        ViaCepDTO viaCepDTO = viaCepClient.buscaEnderecoPor(cep);
        if (viaCepDTO.isErro()) {
            throw new CepInvalidoException("Cep invalido");
        }
        return converterParaEndereco(viaCepDTO);
    }

    public Endereco converterParaEndereco(ViaCepDTO viaCepDTO) {
        Endereco endereco = new Endereco();
        String cep= viaCepDTO.getCep().replaceAll("[^0-9]", "");
        endereco.setCep(cep);
        endereco.setUf(viaCepDTO.getUf());
        endereco.setBairro(viaCepDTO.getBairro());
        endereco.setLocalidade(viaCepDTO.getLocalidade());
        endereco.setLogradouro(viaCepDTO.getLogradouro());
        return endereco;
    }

}

