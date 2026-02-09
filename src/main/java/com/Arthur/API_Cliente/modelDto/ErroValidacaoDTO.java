package com.Arthur.API_Cliente.modelDto;

import com.Arthur.API_Cliente.exception.ErroValidacao;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErroValidacaoDTO {
    private String campo;
    private String mensagem;

    public ErroValidacaoDTO(ErroValidacao erroValidacao) {
        campo = erroValidacao.getCampo();
        mensagem = erroValidacao.getMensagem();
    }
}
