package com.Arthur.API_Cliente;

import com.Arthur.API_Cliente.DTO.ViaCepDTO;
import com.Arthur.API_Cliente.cliente.ViaCepClient;
import com.Arthur.API_Cliente.entity.Endereco;
import com.Arthur.API_Cliente.exception.CepInvalidoException;
import com.Arthur.API_Cliente.exception.ServicoIndisponivelExeption;
import com.Arthur.API_Cliente.mapper.EnderecoMapper;
import com.Arthur.API_Cliente.mapper.EnderecoMapperImpl;
import com.Arthur.API_Cliente.service.ViaCepService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ViaCepServiceTest {
    @InjectMocks
    private ViaCepService viaCepService;
    @Mock
    private ViaCepClient viaCepClient;
    @Spy
    private EnderecoMapper enderecoMapper = new EnderecoMapperImpl();


    @Test
    public void BuscaNaApiSucesso() {
        ViaCepDTO viaCepDTO = new ViaCepDTO("cep", "bairro", "localidade", "uf", "longadouro", null);

        when(viaCepClient.buscaEnderecoPor(anyString())).thenReturn(viaCepDTO);

        Endereco endereco = viaCepService.buscarEnderecoPorCep("11147281", "23");
        Assertions.assertEquals("23", endereco.getNumero());
        Assertions.assertEquals("uf", endereco.getUf());
    }

    @Test
    public void ErroCepInvalido() {
        ViaCepDTO viaCepDTO = new ViaCepDTO("cep", "bairro", "localidade", "uf", "longadouro", true);
        when(viaCepClient.buscaEnderecoPor(anyString())).thenReturn(viaCepDTO);
        Assertions.assertThrows(CepInvalidoException.class, () -> viaCepService.buscarEnderecoPorCep("11147281", "23"));

    }}
