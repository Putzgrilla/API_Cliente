package com.Arthur.API_Cliente.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String logradouro;
    @Column
    private String numero;
    @Column
    private String cep;
    @Column
    private String bairro;
    @Column
    private String localidade;
    @Column
    private String uf;
    @OneToOne(mappedBy = "endereco")
    private Cliente cliente;

}
