package com.br.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "aluno_responsavel")
@Getter
@Setter
public class AlunoResponsavel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno_responsavel")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_responsaveis")
    private Responsavel responsavel;

    @NotBlank
    @Column(name = "parentesco")
    private String parentesco;
} 