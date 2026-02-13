package com.Arthur.API_Cliente.service;

import com.Arthur.API_Cliente.DTO.ClienteAtualizarDTO;
import com.Arthur.API_Cliente.DTO.ClienteDto;
import com.Arthur.API_Cliente.DTO.ClienteSalvarDTO;
import com.Arthur.API_Cliente.entity.Cliente;
import com.Arthur.API_Cliente.entity.Endereco;
import com.Arthur.API_Cliente.exception.NotFoundException;
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

    @Transactional(readOnly = true)
    public Optional<ClienteDto> buscarPorId(Long id) {


        return clienteRepository.findById(id).map(ClienteDto::new);


    }

    @Transactional(readOnly = true)
    public List<ClienteDto> buscarTodos() {

        return clienteRepository.findAll().stream().map(ClienteDto::new).toList();
    }

    @Transactional()
    public Cliente salvar(ClienteSalvarDTO clienteSalvarDTO) {
        Endereco endereco = viaCepService.buscarEnderecoPorCep(clienteSalvarDTO.getCep());
        endereco.setNumero(clienteSalvarDTO.getNumero());
        Cliente cliente = new Cliente();
        cliente.setNascimento(clienteSalvarDTO.getNascimento());
        cliente.setEndereco(endereco);
        cliente.setNome(clienteSalvarDTO.getNome());

        return clienteRepository.save(cliente);
    }

    @Transactional()
    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new NotFoundException(
                    "Cliente com ID " + id + " não encontrado"
            );
        }
        clienteRepository.deleteById(id);
    }

    @Transactional()
    public ClienteDto atualizar(ClienteAtualizarDTO dto, Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente nao encontrado"));
        if (dto.getCep() != null) {
            Endereco endereco = viaCepService.buscarEnderecoPorCep(dto.getCep());
            if (dto.getNumero() != null) {
                endereco.setNumero(dto.getNumero());

            } else {
                endereco.setNumero(cliente.getEndereco().getNumero());
            }
            cliente.setEndereco(endereco);

        }
        if (dto.getNumero() != null && dto.getCep() == null) {
            cliente.getEndereco().setNumero(dto.getNumero());
        }
        if (dto.getNome() != null) cliente.setNome(dto.getNome());
        if (dto.getNascimento() != null) cliente.setNascimento(dto.getNascimento());
        clienteRepository.save(cliente);
        return new ClienteDto(cliente);

    }
}
