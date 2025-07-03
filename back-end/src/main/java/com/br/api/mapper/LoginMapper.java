package com.br.api.mapper;

import com.br.api.dto.login.LoginCreateDTO;
import com.br.api.dto.login.LoginDTO;
import com.br.api.model.Login;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    @Mapping(target = "id_login", source = "id_login")
    @Mapping(target = "nome_usuario", source = "nome_usuario")
    @Mapping(target = "senha", source = "senha")
    @Mapping(target = "tipo_usuario", source = "tipo_usuario")
    @Mapping(target = "id_usuario", source = "id_usuario")
    @Mapping(target = "data_criacao", source = "data_criacao")
    LoginDTO toDTO(Login login);

    @Mapping(target = "id_login", ignore = true)
    @Mapping(target = "nome_usuario", source = "nome_usuario")
    @Mapping(target = "senha", source = "senha")
    @Mapping(target = "tipo_usuario", source = "tipo_usuario")
    @Mapping(target = "id_usuario", source = "id_usuario")
    @Mapping(target = "data_criacao", expression = "java(java.time.LocalDateTime.now())")
    Login toEntity(LoginCreateDTO dto);
}
