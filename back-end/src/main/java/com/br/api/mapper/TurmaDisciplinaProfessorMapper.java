package com.br.api.mapper;

import com.br.api.dto.turmadisciplinaprofessor.TurmaDisciplinaProfessorDTO;
import com.br.api.model.TurmaDisciplinaProfessor;
import com.br.api.model.TurmaDisciplinaProfessorId;
import com.br.api.model.Turma;
import com.br.api.model.Disciplina;
import com.br.api.model.Professor;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class TurmaDisciplinaProfessorMapper {
    @Mapping(target = "idTurma", source = "turma.id_turma")
    @Mapping(target = "idDisciplina", source = "disciplina.id_disciplinas")
    @Mapping(target = "idProfessor", source = "professor.id_professor")
    public abstract TurmaDisciplinaProfessorDTO toDTO(TurmaDisciplinaProfessor entity);

    public TurmaDisciplinaProfessor toEntity(TurmaDisciplinaProfessorDTO dto, Turma turma, Disciplina disciplina, Professor professor) {
        TurmaDisciplinaProfessorId id = new TurmaDisciplinaProfessorId(
            dto.idTurma(),
            dto.idDisciplina(),
            dto.idProfessor()
        );
        TurmaDisciplinaProfessor entity = new TurmaDisciplinaProfessor();
        entity.setId(id);
        entity.setTurma(turma);
        entity.setDisciplina(disciplina);
        entity.setProfessor(professor);
        return entity;
    }
}
