package com.br.api.dto.aluno;

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
