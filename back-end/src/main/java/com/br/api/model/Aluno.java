package com.br.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno")
    @Getter @Setter private int id_aluno;

    @NotBlank
    @Column(name = "nome_aluno")
    @Getter @Setter private String nome_aluno;

    @NotBlank
    @Column(name = "cpf")
    @CPF
    @Getter @Setter private String cpf;

    @NotBlank
    @Column(name = "rg")
    @Getter @Setter private String rg;

    @Column(name = "telefone")
    @Getter @Setter private String telefone;

    @Email
    @Column(name = "email")
    @Getter @Setter private String email;

    @Column(name = "status")
    @Getter @Setter private String status;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    @Getter @Setter private Turma turma;
}
