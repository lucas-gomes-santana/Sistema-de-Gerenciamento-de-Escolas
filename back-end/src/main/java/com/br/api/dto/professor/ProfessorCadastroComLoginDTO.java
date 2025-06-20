package com.br.api.dto.professor;

import org.hibernate.validator.constraints.br.CPF;
import com.br.api.dto.disciplina.DisciplinaDTO;
import com.br.api.dto.turma.TurmaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ProfessorCadastroComLoginDTO(
    @NotBlank String nomeUsuarioGestor,
    @NotBlank String senhaGestor,
    @NotBlank(message = "Nome é obrigatório")
    String nome,
    @NotBlank(message = "CPF é obrigatório")
    @CPF
    String cpf,
    @NotBlank(message = "RG é obrigatório")
    String rg,
    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Telefone inválido")
    String telefone,
    @Email(message = "Email inválido")
    String email,
    @Valid
    DisciplinaDTO disciplina,
    @Valid
    TurmaDTO turma,
    @Valid
    com.br.api.dto.endereco.EnderecoDTO endereco
) {} 