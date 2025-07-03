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
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "rg", source = "rg")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "nomeTurma", source = "turma.nome_turma", defaultValue = "")
    AlunoDTO toDTO(Aluno aluno);

    @Mapping(target = "id", source = "id_aluno")
    @Mapping(target = "nome", source = "nome_aluno")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "rg", source = "rg")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "turma", source = "turma")
    AlunoDetalhesDTO toDetalhesDTO(Aluno aluno);

    @Mapping(target = "id_aluno", ignore = true)
    @Mapping(target = "nome_aluno", source = "nome")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "rg", source = "rg")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "status", expression = "java(com.br.api.model.Aluno.StatusAluno.PRESENTE)")
    @Mapping(target = "turma", ignore = true)
    @Mapping(target = "responsaveis", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    Aluno toEntity(AlunoCadastroDTO dto);
}
