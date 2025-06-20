package com.br.api.dto.responsaveis;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class ResponsaveisDetalhesDTO {
    private Long id_responsaveis;
    
    @NotBlank
    private String nome_responsavel;
    
    @CPF
    @NotBlank
    private String cpf;
    
    @NotBlank
    private String rg;
    
    private String telefone;
}
