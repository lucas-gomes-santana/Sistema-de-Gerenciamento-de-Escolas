package com.br.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tuma")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turma")
    @Getter @Setter private int id_turma;

    @NotBlank
    @Column(name = "nome")
    @Getter @Setter private String nome_turma;

    @NotBlank
    @Column(name = "turno")
    @Getter @Setter private String turno;

}
