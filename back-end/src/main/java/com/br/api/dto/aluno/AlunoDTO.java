package com.br.api.dto.aluno;

public record AlunoDTO(
        int id,
        String nome,
        String cpf,
        String rg,
        String telefone,
        String email,
        String status,
        String turmaNome
) {  }
