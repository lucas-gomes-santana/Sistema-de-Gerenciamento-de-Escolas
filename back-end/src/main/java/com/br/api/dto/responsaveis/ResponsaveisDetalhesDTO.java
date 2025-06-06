package com.br.api.dto.responsaveis;

public record ResponsaveisDetalhesDTO(
    Long id,
    String nome,
    String cpf,
    String rg,
    String telefone
) {  }
