package com.br.api.dto.aluno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlunoCadastroDTO(

        @NotBlank(message = "Nome do aluno é obrigatório!")
        String nome,

        @NotBlank(message = "CPF do aluno é obrigatório!")
        String cpf,

        @NotBlank(message = "Rg do aluno é obrigatório!")
        String rg,

        String telefone,

        @Email(message = "Formato de email inválido!")
        String email,

        @NotNull(message = "ID da turma do aluno é obrigatório")
        int turmaId
) {  }
