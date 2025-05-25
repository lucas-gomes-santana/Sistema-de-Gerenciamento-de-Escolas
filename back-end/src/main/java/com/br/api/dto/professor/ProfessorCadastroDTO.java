package com.br.api.dto.professor;

import jakarta.validation.constraints.NotBlank;

public record ProfessorCadastroDTO(

    @NotBlank(message = "Obrigatório inserir nome do professor!")
    String nome,

    @NotBlank(message = "Obrigatório inserir CPF do professor!")
    String cpf,

    @NotBlank(message = "Obrigatório inserir RG do professor!")
    String rg
) {  }