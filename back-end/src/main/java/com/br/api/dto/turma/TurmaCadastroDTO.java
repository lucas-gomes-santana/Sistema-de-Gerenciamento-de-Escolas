
package com.br.api.dto.turma;

import jakarta.validation.constraints.NotBlank;

public record TurmaCadastroDTO(

    @NotBlank(message = "Obrigatório inserir nome da turma")
    String nome,

    @NotBlank(message = "Obrigatório inserir o turno!")
    String turno

) {  }