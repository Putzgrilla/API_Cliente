package com.Arthur.API_Cliente.DTO;

import com.Arthur.API_Cliente.exception.ErroValidacaoException;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErroValidacaoDTO {
    private String campo;
    private String mensagem;

    public ErroValidacaoDTO(ErroValidacaoException erroValidacaoException) {
        campo = erroValidacaoException.getCampo();
        mensagem = erroValidacaoException.getMensagem();
    }
}
