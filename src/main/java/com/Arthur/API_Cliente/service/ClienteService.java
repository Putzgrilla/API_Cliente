package com.Arthur.API_Cliente.service;

import com.Arthur.API_Cliente.entity.Cliente;
import com.Arthur.API_Cliente.entity.Endereco;
import com.Arthur.API_Cliente.exception.NotFound;
import com.Arthur.API_Cliente.modelDto.ClienteDto;
import com.Arthur.API_Cliente.modelDto.ClienteSalvarDTO;
import com.Arthur.API_Cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ViaCepService viaCepService;

    public Optional<ClienteDto> findById(Long id) {


        return clienteRepository.findById(id).map(ClienteDto::new);


    }

    public List<ClienteDto> findAll() {

        return clienteRepository.findAll().stream().map(ClienteDto::new).toList();
    }

    public Cliente save(ClienteSalvarDTO clienteSalvarDTO) {
        Endereco endereco = viaCepService.buscar(clienteSalvarDTO.getCep());
        endereco.setNumero(clienteSalvarDTO.getNumero());
        Cliente cliente = new Cliente();
        cliente.setNascimento(clienteSalvarDTO.getNascimento());
        cliente.setEndereco(endereco);
        cliente.setNome(clienteSalvarDTO.getNome());

        return clienteRepository.save(cliente);
    }

    public void apagar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new NotFound(
                    "Cliente com ID " + id + " não encontrado"
            );
        }
        clienteRepository.deleteById(id);
    }
}
