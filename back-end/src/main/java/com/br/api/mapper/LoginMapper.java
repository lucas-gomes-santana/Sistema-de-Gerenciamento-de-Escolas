package com.br.api.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import com.br.api.dto.login.*;
import com.br.api.model.Login;

@Component
public class LoginMapper {

    public LoginDTO toDTO(Login login) {
        if (login == null) return null;

        return new LoginDTO(
            login.getId_login(),
            login.getNome_usuario(),
            login.getSenha(),
            login.getTipo_usuario(),
            login.getId_usuario(),
            login.getData_criacao()
        );
    }

    public Login toEntity(LoginCreateDTO dto) {
        if (dto == null) return null;
        Login login = new Login();

        login.setNome_usuario(dto.nome_usuario());
        login.setSenha(dto.senha());
        login.setTipo_usuario(dto.tipo_usuario());
        login.setId_usuario(dto.id_usuario());
        login.setData_criacao(LocalDateTime.now());

        return login;
    }
}
