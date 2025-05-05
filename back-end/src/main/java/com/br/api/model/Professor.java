package com.br.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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

    @Column(name = "cpf")
    @CPF
    @Getter @Setter private String cpf;

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
    @JoinColumn(name = "id_disciplina")
    private Disciplina disciplina;
}
