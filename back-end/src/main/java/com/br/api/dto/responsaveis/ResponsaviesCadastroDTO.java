package com.br.api.dto.responsaveis;

import jakarta.validation.constraints.NotBlank;

public record ResponsaviesCadastroDTO (

    @NotBlank(message = "Obrigatório inserir nome do responsável!")
    String nome,

    @NotBlank(message = "Obrigatório inserir CPF do responsável!")
    String cpf,

    @NotBlank(message = "Obrigatório inserir RG do responsável!")
    String rg,

    String telefone
) {  }
