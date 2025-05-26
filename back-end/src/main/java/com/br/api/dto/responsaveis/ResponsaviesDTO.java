package com.br.api.dto.responsaveis;

public record ResponsaviesDTO (
    int id,
    String nome,
    String cpf,
    String rg,
    String telefone
) {  }
