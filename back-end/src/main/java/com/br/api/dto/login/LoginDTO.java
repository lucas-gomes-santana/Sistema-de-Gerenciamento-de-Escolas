package com.br.api.dto.login;

import java.time.LocalDateTime;

import com.br.api.model.Login.TipoUsuario;

public record LoginDTO (
    Long id_login,
    String nome_usuario,
    String senha,
    TipoUsuario tipo_usuario,
    Long id_usuario,
    LocalDateTime data_criacao
) { }
