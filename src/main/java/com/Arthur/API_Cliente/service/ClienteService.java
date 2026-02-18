package com.Arthur.API_Cliente.service;

import com.Arthur.API_Cliente.DTO.ClienteAtualizarDTO;
import com.Arthur.API_Cliente.DTO.ClienteDto;
import com.Arthur.API_Cliente.DTO.ClienteSalvarDTO;
import com.Arthur.API_Cliente.entity.Cliente;
import com.Arthur.API_Cliente.entity.Endereco;
import com.Arthur.API_Cliente.exception.NotFoundException;

import com.Arthur.API_Cliente.mapper.ClienteMapper;
import com.Arthur.API_Cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ViaCepService viaCepService;
    private final ClienteMapper clienteMapper;


    @Transactional(readOnly = true)
    public Optional<ClienteDto> buscarPorId(Long id) {


        return clienteRepository.findById(id).map(clienteMapper::clienteParaDto);


    }

    @Transactional(readOnly = true)
    public List<ClienteDto> buscarTodos() {

        return clienteRepository.findAll().stream().map(clienteMapper::clienteParaDto).toList();
    }

    @Transactional()
    public ClienteDto salvar(ClienteSalvarDTO dto) {

        Endereco endereco = viaCepService.buscarEnderecoPorCep(dto.cep(), dto.numero());

        Cliente cliente = clienteMapper.clienteSalvarParaCliente(dto);
        cliente.setEndereco(endereco);

        return clienteMapper.clienteParaDto(clienteRepository.save(cliente));
    }

    @Transactional()
    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new NotFoundException("Cliente com ID " + id + " não encontrado");
        }
        clienteRepository.deleteById(id);
    }

    @Transactional()
    public ClienteDto atualizar(ClienteAtualizarDTO dto, Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente nao encontrado"));
        if (dto.cep() != null) cliente.setEndereco(viaCepService.buscarEnderecoPorCep(dto.cep(), dto.numero()));
        if (dto.nome() != null) cliente.setNome(dto.nome());
        if (dto.nascimento() != null) cliente.setNascimento(dto.nascimento());
        clienteRepository.save(cliente);
        return clienteMapper.clienteParaDto(cliente);

    }

}
