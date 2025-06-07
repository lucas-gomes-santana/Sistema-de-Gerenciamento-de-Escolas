package com.br.api.mapper;

import org.springframework.stereotype.Component;
import com.br.api.dto.turmadisciplinaprofessor.TurmaDisciplinaProfessorDTO;
import com.br.api.model.TurmaDisciplinaProfessor;
import com.br.api.model.TurmaDisciplinaProfessorId;
import com.br.api.model.Turma;
import com.br.api.model.Disciplina;
import com.br.api.model.Professor;

@Component
public class TurmaDisciplinaProfessorMapper {
    
    public TurmaDisciplinaProfessorDTO toDTO(TurmaDisciplinaProfessor entity) {
        return new TurmaDisciplinaProfessorDTO(
            entity.getTurma().getId_turma(),
            entity.getDisciplina().getId_disciplinas(),
            entity.getProfessor().getId_professor()
        );
    }

    public TurmaDisciplinaProfessor toEntity(TurmaDisciplinaProfessorDTO dto, 
                                           Turma turma, 
                                           Disciplina disciplina, 
                                           Professor professor) {
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
