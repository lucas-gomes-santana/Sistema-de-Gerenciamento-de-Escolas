package com.br.api.dto.aluno;

import com.br.api.model.Aluno.StatusAluno;

public record AlunoDTO(
        int id,
        String nome,
        String cpf,
        String rg,
        String telefone,
        String email,
        StatusAluno status,
        String nomeTurma
) {  }
