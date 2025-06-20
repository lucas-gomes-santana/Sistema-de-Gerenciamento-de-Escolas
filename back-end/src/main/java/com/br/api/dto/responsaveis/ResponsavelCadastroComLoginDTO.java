package com.br.api.dto.responsaveis;

import org.hibernate.validator.constraints.br.CPF;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.Valid;

public record ResponsavelCadastroComLoginDTO (
    @NotBlank String nomeUsuarioGestor,
    @NotBlank String senhaGestor,
    @NotBlank(message = "Obrigatório inserir nome do responsável!")
    String nome,
    @NotBlank(message = "Obrigatório inserir CPF do responsável!")
    @CPF
    String cpf,
    @NotBlank(message = "Obrigatório inserir RG do responsável!")
    String rg,
    @NotBlank(message = "Obrigatório inserir telefone do responsável!")
    String telefone,
    @Valid
    com.br.api.dto.endereco.EnderecoDTO endereco
) {  }
