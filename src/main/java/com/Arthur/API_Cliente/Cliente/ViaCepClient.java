package com.Arthur.API_Cliente.Cliente;


import com.Arthur.API_Cliente.modelDto.ViaCepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClient {
    @GetMapping("/{cep}/json/")
    ViaCepDTO buscaEnderecoPor(@PathVariable("cep") String cep);
}