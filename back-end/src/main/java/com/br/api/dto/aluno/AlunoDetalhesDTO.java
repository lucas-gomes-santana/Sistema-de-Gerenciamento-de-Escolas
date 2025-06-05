package com.br.api.dto.aluno;

import com.br.api.dto.turma.TurmaDTO;
import com.br.api.model.Aluno.StatusAluno;

public record AlunoDetalhesDTO(
     int id,
     String nome,
     String cpf,
     String rg,
     String telefone,
     String email,
     StatusAluno status,
     TurmaDTO turma
) {  }