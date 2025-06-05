package com.br.api.dto.responsaveis;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResponsaviesCadastroDTO (

    @NotBlank(message = "Obrigatório inserir nome do responsável!")
    String nome,

    @NotBlank(message = "Obrigatório inserir CPF do responsável!")
    String cpf,

    @NotBlank(message = "Obrigatório inserir RG do responsável!")
    String rg,

    @NotBlank(message = "Obrigatório inserir telefone do responsável!")
    @Size(min = 10, max = 11)
    String telefone
) {  }
