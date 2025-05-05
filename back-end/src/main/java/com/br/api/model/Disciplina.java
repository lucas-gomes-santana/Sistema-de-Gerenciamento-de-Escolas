package com.br.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disciplinas")
    @Getter @Setter private int id_disciplinas;

    @Column(name = "nome_disciplinas")
    @Getter @Setter private String nome_disciplinas;
}
