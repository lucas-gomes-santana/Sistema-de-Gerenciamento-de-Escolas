package com.br.api.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.api.dto.aluno.AlunoDTO;
import com.br.api.exception.AlunoException;
import com.br.api.repository.AlunoRepository;
import com.br.api.mapper.AlunoMapper;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;

    private AlunoMapper alunoMapper;

    public List<AlunoDTO> buscarAlunosPorTurma(int turmaId) {
        return alunoRepository.findByTurmaId(turmaId)
            .stream()
            .map(alunoMapper::toDTO)
            .collect(Collectors.toList());
    }

    public AlunoDTO buscarAlunoPorId(int id) {
        return alunoRepository.findById(id)
            .map(alunoMapper::toDTO)
            .orElseThrow(() -> new AlunoException("Aluno não encontrado"));
    }
}
