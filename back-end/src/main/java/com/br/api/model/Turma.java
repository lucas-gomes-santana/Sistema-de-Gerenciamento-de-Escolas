package com.br.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tuma")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turma")
    @Getter @Setter private int id_turma;

    @Column(name = "nome")
    @Getter @Setter private String nome_turma;

    @Column(name = "turno")
    @Getter @Setter private String turno;

    @Column(name = "ano_letivo")
    @Getter @Setter private int ano_letivo;

}
