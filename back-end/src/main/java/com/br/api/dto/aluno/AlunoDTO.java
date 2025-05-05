package com.br.api.dto.aluno;

public record AlunoDTO(
        int id,
        String nome,
        String status,
        String turmaNome
) {  }
