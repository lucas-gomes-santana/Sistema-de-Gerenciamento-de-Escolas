package com.br.api.mapper;

import com.br.api.dto.aluno.*;
import com.br.api.model.Aluno;
import com.br.api.model.Aluno.StatusAluno;
import com.br.api.dto.turma.TurmaDTO;
import org.springframework.stereotype.Component;

@Component
public class AlunoMapper {

    public AlunoDTO toDTO(Aluno aluno) {
        if (aluno == null) return null;

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
        if (aluno == null) return null;

        return new AlunoDetalhesDTO(
            aluno.getId_aluno(),
            aluno.getNome_aluno(),
            aluno.getCpf(),
            aluno.getRg(),
            aluno.getTelefone(),
            aluno.getEmail(),
            aluno.getStatus(),
            aluno.getTurma() != null ? new TurmaDTO(
                aluno.getTurma().getId_turma(),
                aluno.getTurma().getNome_turma(),
                aluno.getTurma().getTurno()
            ) : null
        );
    }

    public Aluno toEntity(AlunoCadastroDTO dto) {
        if (dto == null) return null;

        Aluno aluno = new Aluno();
        aluno.setNome_aluno(dto.nome());
        aluno.setCpf(dto.cpf());
        aluno.setRg(dto.rg());
        aluno.setTelefone(dto.telefone());
        aluno.setEmail(dto.email());
        aluno.setStatus(StatusAluno.PRESENTE);
        return aluno;
    }
}
