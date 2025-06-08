package com.br.api.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoDTO(
    @NotBlank(message = "Rua é obrigatória")
    String rua,

    @NotBlank(message = "Bairro é obrigatório")
    String bairro,

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido")
    String cep,

    String complemento
) {} 