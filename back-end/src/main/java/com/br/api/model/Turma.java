package com.br.api.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "turma")
@Getter
@Setter
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turma")
    private Long id_turma;

    @NotBlank
    @Column(name = "nome_turma")
    private String nome_turma;

    @NotBlank
    @Column(name = "turno")
    private String turno;

    @OneToMany(mappedBy = "turma")
    private List<Aluno> alunos;

}
