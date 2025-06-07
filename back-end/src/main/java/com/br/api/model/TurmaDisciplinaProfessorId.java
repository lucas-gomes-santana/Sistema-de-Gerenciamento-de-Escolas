package com.br.api.model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class TurmaDisciplinaProfessorId implements Serializable {
    
    public TurmaDisciplinaProfessorId(Long idTurma, Long idDisciplina, Long idProfessor) {
        this.idTurma = idTurma;
        this.idDisciplina = idDisciplina;
        this.idProfessor = idProfessor;
    }
    
    private Long idTurma;
    private Long idDisciplina;
    private Long idProfessor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurmaDisciplinaProfessorId that = (TurmaDisciplinaProfessorId) o;
        return Objects.equals(idTurma, that.idTurma) &&
               Objects.equals(idDisciplina, that.idDisciplina) &&
               Objects.equals(idProfessor, that.idProfessor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTurma, idDisciplina, idProfessor);
    }
}