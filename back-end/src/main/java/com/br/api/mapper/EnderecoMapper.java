package com.br.api.mapper;

import com.br.api.dto.endereco.EnderecoDTO;
import com.br.api.model.Endereco;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    @Mapping(target = "rua", source = "nome_rua")
    @Mapping(target = "bairro", source = "nome_bairro")
    @Mapping(target = "cep", source = "cep")
    @Mapping(target = "complemento", source = "complemento")
    EnderecoDTO toDTO(Endereco endereco);

    @Mapping(target = "id_endereco", ignore = true)
    @Mapping(target = "nome_rua", source = "rua")
    @Mapping(target = "nome_bairro", source = "bairro")
    @Mapping(target = "cep", source = "cep")
    @Mapping(target = "complemento", source = "complemento")
    @Mapping(target = "tipo_entidade", ignore = true)
    @Mapping(target = "id_entidade", ignore = true)
    Endereco toEntity(EnderecoDTO dto);
} 