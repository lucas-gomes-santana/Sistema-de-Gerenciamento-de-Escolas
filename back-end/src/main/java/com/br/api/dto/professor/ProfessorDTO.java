package com.br.api.dto.professor;

import com.br.api.model.Professor.StatusProfessor;

public record ProfessorDTO(
    Long id,
    String nome,
    String cpf,
    String rg,
    String telefone,
    String email,
    String status,
    Long disciplinaId,
    String disciplinaNome
) {  }