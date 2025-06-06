package com.br.api.dto.login;

import com.br.api.model.Login.TipoUsuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginCreateDTO (
    
    @NotBlank(message = "O nome do usuário é obrigatório")
    String nome_usuario,

    @NotBlank(message = "A senha é obrigatória")
    String senha,

    @NotNull(message = "O tipo de usuário é obrigatório")
    TipoUsuario tipo_usuario,
    
    @NotNull(message = "O ID do usuário é obrigatório")
    Long id_usuario
) {  }
