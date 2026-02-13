package com.Arthur.API_Cliente.config;

import com.Arthur.API_Cliente.cliente.ViaCepClient;
import com.Arthur.API_Cliente.entity.Cliente;
import com.Arthur.API_Cliente.entity.Endereco;
import com.Arthur.API_Cliente.repository.ClienteRepository;
import com.Arthur.API_Cliente.service.ViaCepService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataLoaderConfig {

    @Bean
    CommandLineRunner initDatabase(ClienteRepository clienteRepository, ViaCepClient viaCepClient, ViaCepService viaCepService) {
        return args -> {

            // Cliente 1 - João Silva
            Cliente cliente1 = new Cliente();
            cliente1.setNome("João Silva");
            cliente1.setNascimento(LocalDate.of(2003, 12, 10));
            Endereco endereco1 = viaCepService.buscarEnderecoPorCep("01310100");
            endereco1.setCliente(cliente1);
            endereco1.setNumero("300");
            cliente1.setEndereco(endereco1);
            clienteRepository.save(cliente1);

            // Cliente 2 - Maria Santos
            Cliente cliente2 = new Cliente();
            cliente2.setNome("Maria Santos");
            cliente2.setNascimento(LocalDate.of(1995, 5, 20));
            Endereco endereco2 = viaCepService.buscarEnderecoPorCep("01310100");
            endereco2.setCliente(cliente2);
            endereco2.setNumero("150");
            cliente2.setEndereco(endereco2);
            clienteRepository.save(cliente2);

        };
    }
}
