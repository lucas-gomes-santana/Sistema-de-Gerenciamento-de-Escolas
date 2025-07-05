package com.br.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.br.api.dto.turma.TurmaCadastroDTO;
import com.br.api.dto.turma.TurmaDTO;
import com.br.api.dto.turma.TurmaDetalhesDTO;
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
        // Verificar se já existe uma turma com este nome
        if (turmaRepository.existsByNome_turma(dto.nome())) {
            throw new TurmaException("Já existe uma turma com este nome!");
        }
        
        // Criar turma usando o construtor com validações
        Turma turma = turmaMapper.toEntity(dto);
        return turmaMapper.toDTO(turmaRepository.save(turma));
    }

    public TurmaDTO buscarPorId(Long id) throws TurmaException {
        return turmaRepository.findById(id)
            .map(turmaMapper::toDTO)
            .orElseThrow(() -> new TurmaException("Turma não encontrada!"));
    }

    public TurmaDetalhesDTO buscarDetalhesPorId(Long id) throws TurmaException {
        return turmaRepository.findById(id)
            .map(turmaMapper::toDetalhesDTO)
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

        // Verificar se o novo nome já existe em outra turma
        if (!turma.getNome_turma().equals(dto.nome()) && turmaRepository.existsByNome_turma(dto.nome())) {
            throw new TurmaException("Já existe uma turma com este nome!");
        }

        // Atualizar dados usando o comportamento da entidade
        turma.atualizarDados(dto.nome(), dto.turno());
        
        return turmaMapper.toDTO(turmaRepository.save(turma));
    }

    public void deletarTurma(Long id) throws TurmaException {
        Turma turma = turmaRepository.findById(id)
            .orElseThrow(() -> new TurmaException("Turma não encontrada!"));
        
        // Verificar se a turma pode ser deletada
        if (turma.temAlunos()) {
            throw new TurmaException("Não é possível deletar uma turma que possui alunos!");
        }
        
        turmaRepository.deleteById(id);
    }
}
