package com.br.api.dto.professor;

public record ProfessorDTO(
    int id,
    String cpf,
    String rg,
    String telefone,
    String email,
    String status
) {  }