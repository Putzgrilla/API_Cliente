package com.Arthur.API_Cliente.controller;


import com.Arthur.API_Cliente.DTO.ClienteAtualizarDTO;
import com.Arthur.API_Cliente.DTO.ClienteDto;
import com.Arthur.API_Cliente.DTO.ClienteSalvarDTO;
import com.Arthur.API_Cliente.service.ClienteService;
import jakarta.persistence.PostUpdate;
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


    @GetMapping()
    public ResponseEntity<List<ClienteDto>> buscarTodos() {
        List<ClienteDto> clientes = clienteService.buscarTodos();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> buscarPorId(@PathVariable("id") Long id) {

        return clienteService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> Delete(@PathVariable("id") Long id) {

        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/salvar")
    public ResponseEntity<ClienteDto> salvar(@Valid @RequestBody() ClienteSalvarDTO cliente) {

        ClienteDto clienteDto = new ClienteDto(clienteService.salvar(cliente));
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteDto);

    }
    @PutMapping("/{id}")
    public  ResponseEntity<ClienteDto> atualizar(@Valid @RequestBody() ClienteAtualizarDTO cliente, @PathVariable Long id){
        ClienteDto DTO = clienteService.atualizar(cliente, id);
        return ResponseEntity.ok(DTO);



    }
}
