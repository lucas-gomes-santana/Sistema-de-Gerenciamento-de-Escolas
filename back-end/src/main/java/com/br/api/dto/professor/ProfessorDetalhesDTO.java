package com.br.api.dto.professor;

import com.br.api.dto.disciplina.DisciplinaDTO;

public record ProfessorDetalhesDTO(
    long id,
    String nome,
    String cpf,
    String rg,
    String telefone,
    String email,
    String status,
    DisciplinaDTO disciplina
) {  }
