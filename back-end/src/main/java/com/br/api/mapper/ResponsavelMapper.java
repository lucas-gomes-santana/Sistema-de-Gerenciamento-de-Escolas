package com.br.api.mapper;

import com.br.api.dto.responsaveis.ResponsavelCadastroDTO;
import com.br.api.dto.responsaveis.ResponsaviesDTO;
import com.br.api.model.Responsavel;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ResponsavelMapper {
    @Mapping(target = "id", source = "id_responsavies")
    @Mapping(target = "nome", source = "nome_responsavel")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "rg", source = "rg")
    @Mapping(target = "telefone", source = "telefone")
    ResponsaviesDTO toDTO(Responsavel responsavel);

    @Mapping(target = "id_responsavies", ignore = true)
    @Mapping(target = "nome_responsavel", source = "nome")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "rg", source = "rg")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(target = "endereco", ignore = true)
    @Mapping(target = "alunos", ignore = true)
    Responsavel toEntity(ResponsavelCadastroDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "nome_responsavel", source = "nome")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "rg", source = "rg")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(target = "endereco", ignore = true)
    @Mapping(target = "id_responsavies", ignore = true)
    @Mapping(target = "alunos", ignore = true)
    void updateEntity(@MappingTarget Responsavel responsavel, ResponsavelCadastroDTO dto);
} 