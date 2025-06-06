package com.br.api.dto.professor;

import com.br.api.dto.disciplina.DisciplinaDTO;
import com.br.api.model.Professor.StatusProfessor;

public record ProfessorDetalhesDTO(
    Long id,
    String nome,
    String cpf,
    String rg,
    String telefone,
    String email,
    StatusProfessor status,
    DisciplinaDTO disciplina
) {  }
