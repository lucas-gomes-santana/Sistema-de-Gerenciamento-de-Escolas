package com.br.api.dto.professor;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProfessorCadastroDTO(

    @NotBlank(message = "Obrigatório inserir nome do professor!")
    String nome,

    @NotBlank(message = "Obrigatório inserir CPF do professor!")
    @CPF(message = "CPF inválido!")
    String cpf,

    @NotBlank(message = "Obrigatório inserir RG do professor!")
    @Pattern(regexp = "^\\d{7,14}$", message = "RG inválido")
    String rg,

    @NotBlank(message = "Obrigatório inserir telefone do professor!")
    @Size(min = 10, max = 11)
    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone inválido")
    String telefone,

    @Email(message = "Email inválido!")
    String email
) {  }