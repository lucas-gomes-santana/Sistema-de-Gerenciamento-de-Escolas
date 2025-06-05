package com.br.api.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class TurmaDisciplinaProfessorId implements Serializable {
    private int idTurma;
    private int idDisciplina;
    private int idProfessor;
}