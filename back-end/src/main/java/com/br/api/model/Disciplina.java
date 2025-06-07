package com.br.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "disciplinas")
@Getter
@Setter
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disciplinas")
    private Long id_disciplinas;

    @Column(name = "nome_disciplina")
    private String nome_disciplina;
}
