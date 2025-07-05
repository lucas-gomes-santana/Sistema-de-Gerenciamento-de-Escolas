package com.br.api.dto.turma;

import com.br.api.dto.aluno.AlunoDTO;
import com.br.api.dto.turmadisciplinaprofessor.TurmaDisciplinaProfessorDTO;
import java.util.List;

public record TurmaDetalhesDTO(
    Long id,
    String nomeTurma,
    String turno,
    int quantidadeAlunos,
    int quantidadeDisciplinas,
    List<AlunoDTO> alunos,
    List<TurmaDisciplinaProfessorDTO> disciplinas
) {  } 