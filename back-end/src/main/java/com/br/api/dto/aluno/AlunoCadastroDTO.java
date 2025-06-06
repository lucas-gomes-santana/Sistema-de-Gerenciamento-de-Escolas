package com.br.api.dto.aluno;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record AlunoCadastroDTO(

        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        String nome,

        @NotBlank(message = "CPF é obrigatório")
        @CPF(message = "CPF inválido")
        String cpf,

        @NotBlank(message = "RG é obrigatório")
        @Pattern(regexp = "^\\d{7,14}$", message = "RG inválido")
        String rg,

        @Pattern(regexp = "^\\d{10,11}$", message = "Telefone inválido")
        @Size(min = 10, max = 11)
        String telefone,

        @Email(message = "Email inválido")
        String email,

        @NotNull(message = "ID da turma é obrigatório")
        Long turmaId
) {  }
