package com.br.api.dto.aluno;

import com.br.api.dto.turma.TurmaDTO;
import com.br.api.dto.endereco.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroAlunoComLoginDTO(
    @NotBlank String nomeUsuarioGestor,
    @NotBlank String senhaGestor,
    @NotBlank String nome,
    @NotBlank String nomeUsuario,
    @NotBlank String senha,
    @NotBlank String cpf,
    @NotBlank String rg,
    @NotBlank String telefone,
    @NotBlank String email,
    @NotNull String tipoUsuario,
    @Valid @NotNull TurmaDTO turma,
    @Valid @NotNull EnderecoDTO endereco
) {} 