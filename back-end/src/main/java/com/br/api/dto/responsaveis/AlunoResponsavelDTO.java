package com.br.api.dto.responsaveis;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlunoResponsavelDTO {
    private Long id;
    
    @NotNull
    private Long idAluno;
    
    @NotNull
    private Long idResponsavel;
    
    @NotBlank
    private String parentesco;
} 