package com.Arthur.API_Cliente.exception;

public class NotFound extends RuntimeException {
    public NotFound(String mensagem) {
        super(mensagem);
    }
}
