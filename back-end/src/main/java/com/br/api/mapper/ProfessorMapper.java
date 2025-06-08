package com.br.api.mapper;

import org.springframework.stereotype.Component;
import com.br.api.dto.professor.ProfessorCadastroDTO;
import com.br.api.dto.professor.ProfessorDTO;
import com.br.api.model.Professor;

@Component
public class ProfessorMapper {

    public ProfessorDTO toDTO(Professor professor) {
        if (professor == null) {
            return null;
        }

        return new ProfessorDTO(
            professor.getId_professor(),
            professor.getNome_professor(),
            professor.getCpf(),
            professor.getRg(),
            professor.getTelefone(),
            professor.getEmail(),
            professor.getStatus(),
            professor.getDisciplinas() != null ? professor.getDisciplinas().getId_disciplinas() : null,
            professor.getDisciplinas() != null ? professor.getDisciplinas().getNome_disciplina() : null
        );
    }

    public Professor toEntity(ProfessorCadastroDTO dto) {
        if (dto == null) {
            return null;
        }

        Professor professor = new Professor();
        professor.setNome_professor(dto.nome());
        professor.setCpf(dto.cpf());
        professor.setRg(dto.rg());
        professor.setTelefone(dto.telefone());
        professor.setEmail(dto.email());
        professor.setStatus("Ativo");

        return professor;
    }
}
