package com.br.api.mapper;

import com.br.api.dto.login.LoginCreateDTO;
import com.br.api.dto.login.LoginDTO;
import com.br.api.model.Login;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    LoginDTO toDTO(Login login);

    @Mapping(target = "id_login", ignore = true)
    @Mapping(target = "data_criacao", expression = "java(java.time.LocalDateTime.now())")
    Login toEntity(LoginCreateDTO dto);
}
