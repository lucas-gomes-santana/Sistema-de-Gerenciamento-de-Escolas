package com.br.api.mapper;

import com.br.api.dto.aluno.*;
import com.br.api.model.Aluno;
import com.br.api.dto.turma.TurmaDTO;

public class AlunoMapper {

    public AlunoDTO toDTO(Aluno aluno) {
        return new AlunoDTO(
                aluno.getId_aluno(),
                aluno.getNome_aluno(),
                aluno.getCpf(),
                aluno.getRg(),
                aluno.getTelefone(),
                aluno.getEmail(),
                aluno.getStatus(),
                aluno.getTurma() != null ? aluno.getTurma().getNome_turma() : null
        );
    }

    public AlunoDetalhesDTO toDetalhesDTO(Aluno aluno) {
        return new AlunoDetalhesDTO(
                (long) aluno.getId_aluno(),
                aluno.getNome_aluno(),
                aluno.getCpf(),
                aluno.getRg(),
                aluno.getTelefone(),
                aluno.getEmail(),
                aluno.getStatus(),
                aluno.getTurma() != null ? new TurmaDTO(
                    aluno.getTurma().getId_turma(),
                    aluno.getTurma().getNome_turma()
                ) : null
        );
    }

    public Aluno toEntity(AlunoCadastroDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome_aluno(dto.nome());
        aluno.setCpf(dto.cpf());
        aluno.setRg(dto.rg());
        aluno.setTelefone(dto.telefone());
        aluno.setEmail(dto.email());

        return aluno;
    }
}
