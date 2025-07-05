package com.br.api.mapper;

import com.br.api.dto.responsaveis.ResponsavelCadastroDTO;
import com.br.api.dto.responsaveis.ResponsaviesDTO;
import com.br.api.dto.responsaveis.ResponsaveisDetalhesDTO;
import com.br.api.model.Responsavel;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AlunoMapper.class})
public interface ResponsavelMapper {
    @Mapping(target = "id", source = "id_responsavies")
    @Mapping(target = "nome", source = "nome_responsavel")
    @Mapping(target = "telefone", source = "telefone")
    ResponsaviesDTO toDTO(Responsavel responsavel);

    // Método customizado para toDetalhesDTO
    default ResponsaveisDetalhesDTO toDetalhesDTO(Responsavel responsavel) {
        if (responsavel == null) {
            return null;
        }

        return new ResponsaveisDetalhesDTO(
            responsavel.getId_responsavies(),
            responsavel.getNome_responsavel(),
            responsavel.getTelefone(),
            responsavel.getAlunos().stream()
                .map(aluno -> new com.br.api.dto.aluno.AlunoDTO(
                    aluno.getId_aluno(),
                    aluno.getNome_aluno(),
                    aluno.getTelefone(),
                    aluno.getEmail(),
                    aluno.getStatus(),
                    aluno.getTurma() != null ? aluno.getTurma().getNome_turma() : null
                ))
                .toList()
        );
    }

    // Método para criar entidade a partir do DTO de cadastro
    default Responsavel toEntity(ResponsavelCadastroDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Responsavel(
            dto.nome(),
            dto.cpf(),
            dto.rg(),
            dto.telefone()
        );
    }


} 