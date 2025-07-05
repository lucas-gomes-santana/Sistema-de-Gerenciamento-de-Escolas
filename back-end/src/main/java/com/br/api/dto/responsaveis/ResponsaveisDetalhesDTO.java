package com.br.api.dto.responsaveis;

import com.br.api.dto.aluno.AlunoDTO;
import java.util.List;

public record ResponsaveisDetalhesDTO(
    Long id,
    String nome,
    String telefone,
    List<AlunoDTO> alunos
) {  }
