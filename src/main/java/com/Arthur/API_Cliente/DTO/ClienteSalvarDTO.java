package com.Arthur.API_Cliente.DTO;

import jakarta.validation.constraints.*;

import java.time.LocalDate;


public record ClienteSalvarDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nome,

        @NotNull(message = "A data de nascimento é obrigatória")
        @Past(message = "A data de nascimento deve ser uma data passada")
        LocalDate nascimento,

        @NotBlank(message = "O CEP é obrigatório")
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido")
        String cep,

        @NotBlank(message = "O número do endereço é obrigatório")
        String numero) {
}


