package com.br.api.dto.aluno;

import com.br.api.dto.turma.TurmaDTO;

public record AlunoDetalhesDTO(
     Long id,
     String nome,
     String cpf,
     String rg,
     String telefone,
     String email,
     String status,
     TurmaDTO turma
) {  }
