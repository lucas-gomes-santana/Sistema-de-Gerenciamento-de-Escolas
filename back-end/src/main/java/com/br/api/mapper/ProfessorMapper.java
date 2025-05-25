package com.br.api.mapper;

import com.br.api.dto.professor.ProfessorDTO;
import com.br.api.model.Professor;

public class ProfessorMapper {
    
    public ProfessorDTO toDTO(Professor professor) {
        return new ProfessorDTO(
            professor.getId_disciplinas(),
            professor.getCpf(),
            professor.getRg(),
            professor.getEmail(),
            professor.getTelefone(),
        );
    }
}
