package com.br.api.dto.aluno;

import com.br.api.dto.turma.TurmaDTO;
import com.br.api.model.Aluno.StatusAluno;

public record AlunoDetalhesDTO(
     Long id,
     String nome,
     String telefone,
     String email,
     StatusAluno status,
     TurmaDTO turma
) {  }