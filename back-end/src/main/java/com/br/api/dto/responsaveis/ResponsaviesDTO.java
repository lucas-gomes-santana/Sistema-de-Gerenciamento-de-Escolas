package com.br.api.dto.responsaveis;

public record ResponsaviesDTO (
    Long id,
    String nome,
    String cpf,
    String rg,
    String telefone
) {  }
