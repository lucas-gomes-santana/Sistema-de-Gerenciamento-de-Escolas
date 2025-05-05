package com.br.api.model;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "responsaveis")
public class Responsavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_responsaveis")
    @Getter @Setter private int id_responsavies;

    @Column(name = "nome")
    @Getter @Setter private String nome;

    @CPF
    @Column(name = "cpf")
    @Getter @Setter private String cpf;

    @Column(name = "rg")
    @Getter @Setter private String rg;

    @Column(name = "telefone")
    @Getter @Setter private String telefone;
}
