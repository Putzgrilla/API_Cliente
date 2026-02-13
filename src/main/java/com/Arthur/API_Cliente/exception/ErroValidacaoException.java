package com.Arthur.API_Cliente.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErroValidacaoException extends RuntimeException {
    private String campo;
    private String mensagem;


}
