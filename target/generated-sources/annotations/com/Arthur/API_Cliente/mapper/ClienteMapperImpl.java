package com.Arthur.API_Cliente.mapper;

import com.Arthur.API_Cliente.DTO.ClienteDto;
import com.Arthur.API_Cliente.DTO.ClienteSalvarDTO;
import com.Arthur.API_Cliente.DTO.EnderecoDTO;
import com.Arthur.API_Cliente.entity.Cliente;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-18T20:25:11-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Override
    public ClienteDto clienteParaDto(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        EnderecoDTO endereco = null;

        id = cliente.getId();
        nome = cliente.getNome();
        endereco = enderecoMapper.enderecoParaDto( cliente.getEndereco() );

        Integer idade = calcularIdade(cliente.getNascimento());

        ClienteDto clienteDto = new ClienteDto( id, nome, idade, endereco );

        return clienteDto;
    }

    @Override
    public Cliente clienteSalvarParaCliente(ClienteSalvarDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setNome( dto.nome() );
        cliente.setNascimento( dto.nascimento() );

        return cliente;
    }
}
