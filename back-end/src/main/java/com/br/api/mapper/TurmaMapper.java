package com.br.api.mapper;

import com.br.api.dto.turma.TurmaCadastroDTO;
import com.br.api.dto.turma.TurmaDTO;
import com.br.api.dto.turma.TurmaDetalhesDTO;
import com.br.api.model.Turma;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AlunoMapper.class, TurmaDisciplinaProfessorMapper.class})
public interface TurmaMapper {
    @Mapping(target = "id", source = "id_turma")
    @Mapping(target = "nomeTurma", source = "nome_turma")
    @Mapping(target = "turno", source = "turno")
    TurmaDTO toDTO(Turma turma);

    @Mapping(target = "id", source = "id_turma")
    @Mapping(target = "nomeTurma", source = "nome_turma")
    @Mapping(target = "turno", source = "turno")
    @Mapping(target = "quantidadeAlunos", expression = "java(turma.getQuantidadeAlunos())")
    @Mapping(target = "quantidadeDisciplinas", expression = "java(turma.getQuantidadeDisciplinas())")
    @Mapping(target = "alunos", source = "alunos")
    @Mapping(target = "disciplinas", source = "turmaDisciplinaProfessores")
    TurmaDetalhesDTO toDetalhesDTO(Turma turma);

    // Método para criar entidade a partir do DTO de cadastro
    default Turma toEntity(TurmaCadastroDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Turma(dto.nome(), dto.turno());
    }
}
