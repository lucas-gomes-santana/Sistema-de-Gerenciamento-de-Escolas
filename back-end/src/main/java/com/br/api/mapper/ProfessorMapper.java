package com.br.api.mapper;

import com.br.api.dto.professor.ProfessorCadastroDTO;
import com.br.api.dto.professor.ProfessorDTO;
import com.br.api.dto.professor.ProfessorDetalhesDTO;
import com.br.api.model.Professor;
import com.br.api.model.Disciplina;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {
    
    @Mapping(target = "id", source = "id_professor")
    @Mapping(target = "nome", source = "nome_professor")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "disciplinaId", source = "disciplinas.id_disciplinas")
    @Mapping(target = "disciplinaNome", source = "disciplinas.nome_disciplina")
    ProfessorDTO toDTO(Professor professor);

    // Método customizado para toDetalhesDTO
    default ProfessorDetalhesDTO toDetalhesDTO(Professor professor) {
        if (professor == null) {
            return null;
        }

        com.br.api.dto.disciplina.DisciplinaDTO disciplinaDTO = null;
        if (professor.getDisciplinas() != null) {
            disciplinaDTO = new com.br.api.dto.disciplina.DisciplinaDTO(
                professor.getDisciplinas().getId_disciplinas(),
                professor.getDisciplinas().getNome_disciplina()
            );
        }

        return new ProfessorDetalhesDTO(
            professor.getId_professor(),
            professor.getNome_professor(),
            professor.getTelefone(),
            professor.getEmail(),
            professor.getStatus(),
            disciplinaDTO
        );
    }

    // Método para criar entidade a partir do DTO de cadastro
    default Professor toEntity(ProfessorCadastroDTO dto, Disciplina disciplina) {
        if (dto == null) {
            return null;
        }

        return new Professor(
            dto.nome(),
            dto.cpf(),
            dto.rg(),
            dto.telefone(),
            dto.email(),
            disciplina
        );
    }
}
