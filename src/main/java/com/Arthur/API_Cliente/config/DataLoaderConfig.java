package com.Arthur.API_Cliente.config;

import com.Arthur.API_Cliente.entity.Cliente;
import com.Arthur.API_Cliente.entity.Endereco;
import com.Arthur.API_Cliente.repository.ClienteRepository;
import com.Arthur.API_Cliente.service.ViaCepService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
@Profile("dev")
@Configuration
public class DataLoaderConfig {

    @Bean
    CommandLineRunner initDatabase(ClienteRepository clienteRepository, ViaCepService viaCepService) {
        return args -> {

            // Cliente 1 - João Silva
            Cliente cliente1 = new Cliente();
            cliente1.setNome("João Silva");
            cliente1.setNascimento(LocalDate.of(2003, 12, 10));
            Endereco endereco1 = viaCepService.buscarEnderecoPorCep("01310100", "10");
            cliente1.setEndereco(endereco1);
            clienteRepository.save(cliente1);
            // Cliente 2 - Maria Santos
            Cliente cliente2 = new Cliente();
            cliente2.setNome("Maria Santos");
            cliente2.setNascimento(LocalDate.of(1995, 5, 20));
            Endereco endereco2 = viaCepService.buscarEnderecoPorCep("01310100", "12");
            cliente2.setEndereco(endereco2);
            clienteRepository.save(cliente2);

        };
    }
}
