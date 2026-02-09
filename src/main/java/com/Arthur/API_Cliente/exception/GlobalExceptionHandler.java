package com.Arthur.API_Cliente.exception;

import com.Arthur.API_Cliente.modelDto.ErroValidacaoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<String> NotFound(NotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(CepException.class)
    public ResponseEntity<String>CepInvalido(CepException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroValidacaoDTO>> Errovalidacao(MethodArgumentNotValidException e){
        List<ErroValidacao> erros =e.getBindingResult().getFieldErrors().stream().map(p-> new ErroValidacao(p.getField(),p.getDefaultMessage())).toList();
        List<ErroValidacaoDTO> DTO = erros.stream().map(ErroValidacaoDTO::new).toList();
        return ResponseEntity.badRequest().body(DTO);
    }
}
