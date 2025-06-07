package com.br.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.EmbeddedId;

@Entity
@Table(name = "turma_disciplina_professor")
@Getter
@Setter
@NoArgsConstructor
public class TurmaDisciplinaProfessor {

    @EmbeddedId
    private TurmaDisciplinaProfessorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idTurma")
    @JoinColumn(name = "id_turma")
    private Turma turma;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idDisciplina")
    @JoinColumn(name = "id_disciplinas")
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProfessor")
    @JoinColumn(name = "id_professor")
    private Professor professor;
}