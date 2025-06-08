package com.br.api.dto.aluno;

import com.br.api.dto.turma.TurmaDTO;
import com.br.api.dto.endereco.EnderecoDTO;
import jakarta.validation.Valid;
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
        String rg,

        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Telefone inválido")
        String telefone,

        @Email(message = "Email inválido")
        String email,

        @Valid
        @NotNull(message = "Turma é obrigatória")
        TurmaDTO turma,

        @Valid
        @NotNull(message = "Endereço é obrigatório")
        EnderecoDTO endereco
) {}
