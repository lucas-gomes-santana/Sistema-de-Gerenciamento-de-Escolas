package com.br.api.dto.professor;

public record ProfessorDTO(
    Long id,
    String nome,
    String telefone,
    String email,
    String status,
    Long disciplinaId,
    String disciplinaNome
) {  }