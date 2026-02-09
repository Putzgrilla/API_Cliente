package com.Arthur.API_Cliente.controller;


import com.Arthur.API_Cliente.modelDto.ClienteDto;
import com.Arthur.API_Cliente.modelDto.ClienteSalvarDTO;
import com.Arthur.API_Cliente.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;


    @GetMapping("/all")
    public ResponseEntity<List<ClienteDto>> findAll() {
        List<ClienteDto> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> findById(@PathVariable("id") Long id) {

        return clienteService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletar{id}")
    public void Apagar(@PathVariable("id") Long id) {

        clienteService.apagar(id);

    }

    @PostMapping("/salvar")
    public ResponseEntity<ClienteDto> salvar(@Valid @RequestBody() ClienteSalvarDTO cliente) {

        ClienteDto clienteDto = new ClienteDto(clienteService.save(cliente));
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteDto);

    }
}
