package com.br.api.mapper;

import com.br.api.dto.turma.TurmaCadastroDTO;
import com.br.api.dto.turma.TurmaDTO;
import com.br.api.model.Turma;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TurmaMapper {
    @Mapping(target = "id", source = "id_turma")
    @Mapping(target = "nomeTurma", source = "nome_turma")
    @Mapping(target = "turno", source = "turno")
    TurmaDTO toDTO(Turma turma);

    @Mapping(target = "id_turma", ignore = true)
    @Mapping(target = "nome_turma", source = "nome")
    @Mapping(target = "turno", source = "turno")
    @Mapping(target = "alunos", ignore = true)
    @Mapping(target = "turmaDisciplinaProfessores", ignore = true)
    Turma toEntity(TurmaCadastroDTO dto);

    @Mapping(target = "nome_turma", source = "nome")
    @Mapping(target = "turno", source = "turno")
    @Mapping(target = "id_turma", ignore = true)
    @Mapping(target = "alunos", ignore = true)
    @Mapping(target = "turmaDisciplinaProfessores", ignore = true)
    void updateEntity(@MappingTarget Turma turma, TurmaCadastroDTO dto);
}
