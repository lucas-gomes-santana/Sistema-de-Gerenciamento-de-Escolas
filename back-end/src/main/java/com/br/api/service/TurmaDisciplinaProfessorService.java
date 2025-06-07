package com.br.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.br.api.dto.turmadisciplinaprofessor.TurmaDisciplinaProfessorDTO;
import com.br.api.exception.DisciplinaNotFoundException;
import com.br.api.exception.ProfessorNotFoundException;
import com.br.api.exception.TurmaNotFoundException;
import com.br.api.mapper.TurmaDisciplinaProfessorMapper;
import com.br.api.model.TurmaDisciplinaProfessor;
import com.br.api.repository.DisciplinaRepository;
import com.br.api.repository.ProfessorRepository;
import com.br.api.repository.TurmaDisciplinaProfessorRepository;
import com.br.api.repository.TurmaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TurmaDisciplinaProfessorService {
    
    private final TurmaDisciplinaProfessorRepository repository;
    private final TurmaRepository turmaRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;
    private final TurmaDisciplinaProfessorMapper mapper;

    public List<TurmaDisciplinaProfessorDTO> listarTodos() {
        return repository.findAll()
            .stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }

    public List<TurmaDisciplinaProfessorDTO> buscarPorTurma(Long turmaId) {
        return repository.findByTurmaId(turmaId)
            .stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }

    public List<TurmaDisciplinaProfessorDTO> buscarPorProfessor(Long professorId) {
        return repository.findByProfessorId(professorId)
            .stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }

    public List<TurmaDisciplinaProfessorDTO> buscarPorDisciplina(Long disciplinaId) {
        return repository.findByDisciplinaId(disciplinaId)
            .stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }

    public TurmaDisciplinaProfessorDTO vincular(TurmaDisciplinaProfessorDTO dto) 
    throws TurmaNotFoundException, DisciplinaNotFoundException, ProfessorNotFoundException {

        // Verificar se as entidades existem
        var turma = turmaRepository.findById(dto.idTurma())
            .orElseThrow(() -> new TurmaNotFoundException("Turma não encontrada"));
        
        var disciplina = disciplinaRepository.findById(dto.idDisciplina())
            .orElseThrow(() -> new DisciplinaNotFoundException("Disciplina não encontrada"));
        
        var professor = professorRepository.findById(dto.idProfessor())
            .orElseThrow(() -> new ProfessorNotFoundException("Professor não encontrado"));

        TurmaDisciplinaProfessor entity = mapper.toEntity(dto, turma, disciplina, professor);
        entity = repository.save(entity);
        return mapper.toDTO(entity);
    }

    public void desvincular(Long turmaId, Long disciplinaId, Long professorId) {
        repository.deleteById(turmaId);
    }

}
