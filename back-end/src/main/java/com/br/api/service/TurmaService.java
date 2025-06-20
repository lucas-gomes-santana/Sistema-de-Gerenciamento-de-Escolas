package com.br.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.br.api.dto.turma.TurmaCadastroDTO;
import com.br.api.dto.turma.TurmaDTO;
import com.br.api.exception.TurmaException;
import com.br.api.mapper.TurmaMapper;
import com.br.api.model.Turma;
import com.br.api.repository.TurmaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TurmaService {
    private final TurmaRepository turmaRepository;
    private final TurmaMapper turmaMapper;

    public TurmaDTO criarTurma(TurmaCadastroDTO dto) throws TurmaException {
        if (turmaRepository.existsByNome_turma(dto.nome())) {
            throw new TurmaException("Já existe uma turma com este nome!");
        }
        Turma turma = turmaMapper.toEntity(dto);
        return turmaMapper.toDTO(turmaRepository.save(turma));
    }

    public TurmaDTO buscarPorId(Long id) throws TurmaException {
        return turmaRepository.findById(id)
            .map(turmaMapper::toDTO)
            .orElseThrow(() -> new TurmaException("Turma não encontrada!"));
    }

    public List<TurmaDTO> listarTodas() {
        return turmaRepository.findAll().stream()
            .map(turmaMapper::toDTO)
            .collect(Collectors.toList());
    }

    public TurmaDTO atualizar(Long id, TurmaCadastroDTO dto) throws TurmaException {
        Turma turma = turmaRepository.findById(id)
            .orElseThrow(() -> new TurmaException("Turma não encontrada!"));

        // Atualiza os campos da turma com os dados do DTO
        turmaMapper.updateEntity(turma, dto);
        
        // Salva a turma atualizada
        return turmaMapper.toDTO(turmaRepository.save(turma));
    }

    public void deletarTurma(Long id) throws TurmaException {
        if(!turmaRepository.existsById(id)) {
            throw new TurmaException("Turma não encontrada!");
        }
        turmaRepository.deleteById(id);
    }
}
