package com.Arthur.API_Cliente.service;

import com.Arthur.API_Cliente.Cliente.ViaCepClient;
import com.Arthur.API_Cliente.entity.Endereco;
import com.Arthur.API_Cliente.exception.CepException;
import com.Arthur.API_Cliente.modelDto.ViaCepDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ViaCepService {

    private final ViaCepClient viaCepClient;

    public Endereco buscar(String cep) {

        ViaCepDTO viaCepDTO = viaCepClient.buscaEnderecoPor(cep);
        if (viaCepDTO.isErro()) {
            throw new CepException("Cep invalido");
        }
        return viacepDTOParaEndereco(viaCepDTO);
    }

    public Endereco viacepDTOParaEndereco(ViaCepDTO viaCepDTO) {
        Endereco endereco = new Endereco();
        endereco.setCep(viaCepDTO.getCep());
        endereco.setUf(viaCepDTO.getUf());
        endereco.setBairro(viaCepDTO.getBairro());
        endereco.setLocalidade(viaCepDTO.getLocalidade());
        endereco.setLogradouro(viaCepDTO.getLogradouro());
        return endereco;
    }

}

