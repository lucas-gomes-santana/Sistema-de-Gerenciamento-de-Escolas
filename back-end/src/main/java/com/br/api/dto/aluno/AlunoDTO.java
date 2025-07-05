package com.br.api.dto.aluno;

import com.br.api.model.Aluno.StatusAluno;

public record AlunoDTO(
        Long id,
        String nome,
        String telefone,
        String email,
        StatusAluno status,
        String nomeTurma
) {  }
