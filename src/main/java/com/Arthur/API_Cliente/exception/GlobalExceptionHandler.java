package com.Arthur.API_Cliente.exception;

import com.Arthur.API_Cliente.DTO.ErroValidacaoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> NotFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(CepInvalidoException.class)
    public ResponseEntity<String>CepInvalido(CepInvalidoException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroValidacaoDTO>> Errovalidacao(MethodArgumentNotValidException e){
        List<ErroValidacaoException> erros =e.getBindingResult().getFieldErrors().stream().map(p-> new ErroValidacaoException(p.getField(),p.getDefaultMessage())).toList();
        List<ErroValidacaoDTO> DTO = erros.stream().map(ErroValidacaoDTO::new).toList();
        return ResponseEntity.badRequest().body(DTO);
    }
}
