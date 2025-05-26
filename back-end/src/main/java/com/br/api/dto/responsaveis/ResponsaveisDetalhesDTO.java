package com.br.api.dto.responsaveis;

public record ResponsaveisDetalhesDTO(
    long id,
    String nome,
    String cpf,
    String rg,
    String telefone
) {  }
