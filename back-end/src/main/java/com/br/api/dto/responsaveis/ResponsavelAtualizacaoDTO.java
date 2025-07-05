package com.br.api.dto.responsaveis;

import com.br.api.dto.endereco.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ResponsavelAtualizacaoDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        String nome,

        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Telefone inválido")
        String telefone,

        @Valid
        EnderecoDTO endereco
) {} 