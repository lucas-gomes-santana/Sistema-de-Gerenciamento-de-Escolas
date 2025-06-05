package com.br.api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "responsaveis")
@Getter
@Setter
public class Responsavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_responsaveis")
    private int id_responsavies;

    @Column(name = "nome")
    @NotBlank    
    private String nome_professor;

    @CPF
    @Column(name = "cpf")
    @NotBlank
    private String cpf;

    @Column(name = "rg")
    @NotBlank
    private String rg;

    @Column(name = "telefone")
    private String telefone;
}
