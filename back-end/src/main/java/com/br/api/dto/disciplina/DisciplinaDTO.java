package com.br.api.dto.disciplina;

import jakarta.validation.constraints.NotBlank;

public record DisciplinaDTO(
    Long id,
    @NotBlank(message = "Nome da disciplina é obrigatório")
    String nomeDisciplina
) {}
