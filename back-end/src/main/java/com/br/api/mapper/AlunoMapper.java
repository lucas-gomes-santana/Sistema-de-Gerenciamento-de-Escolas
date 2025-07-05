package com.br.api.mapper;

import com.br.api.dto.aluno.AlunoCadastroDTO;
import com.br.api.dto.aluno.AlunoDTO;
import com.br.api.dto.aluno.AlunoDetalhesDTO;
import com.br.api.model.Aluno;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = {TurmaMapper.class})
public interface AlunoMapper {
    @Mapping(target = "id", source = "id_aluno")
    @Mapping(target = "nome", source = "nome_aluno")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "nomeTurma", source = "turma.nome_turma", defaultValue = "")
    AlunoDTO toDTO(Aluno aluno);

    @Mapping(target = "id", source = "id_aluno")
    @Mapping(target = "nome", source = "nome_aluno")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "turma", source = "turma")
    AlunoDetalhesDTO toDetalhesDTO(Aluno aluno);

    // Método para criar entidade a partir do DTO de cadastro
    default Aluno toEntity(AlunoCadastroDTO dto, com.br.api.model.Turma turma) {
        return new Aluno(
            dto.nome(),
            dto.cpf(),
            dto.rg(),
            dto.telefone(),
            dto.email(),
            turma
        );
    }
}
