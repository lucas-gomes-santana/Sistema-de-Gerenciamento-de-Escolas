package com.br.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor")
    @Getter @Setter private int id_professor;

    @NotBlank
    @Column(name = "nome_professor")
    @Getter @Setter private String nome;

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

    @Column(name = "id_disciplinas")
    @Getter @Setter private int id_disciplinas;

    @Column(name = "status")
    @Getter @Setter private String status;

    @ManyToOne
    @JoinColumn(name = "disciplinas_id_disciplina")
    @Getter @Setter private Disciplina disciplina;
}
