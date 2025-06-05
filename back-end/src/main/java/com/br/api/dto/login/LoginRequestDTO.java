package com.br.api.dto.login;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO (
    @NotBlank(message = "O nome do usuário é obrigatório")
    String nome_usuario,

    @NotBlank(message = "A senha é obrigatória")
    String senha
) {
}
