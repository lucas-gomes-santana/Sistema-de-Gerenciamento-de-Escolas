package com.br.api.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.br.api.dto.turmadisciplinaprofessor.TurmaDisciplinaProfessorDTO;
import com.br.api.service.TurmaDisciplinaProfessorService;
import com.br.api.exception.TurmaException;
import com.br.api.exception.ProfessorException;
import com.br.api.exception.DisciplinaException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/turma-disciplina-professor")
@RequiredArgsConstructor
public class TurmaDisciplinaProfessorController {

    private final TurmaDisciplinaProfessorService service;

    @GetMapping
    public ResponseEntity<List<TurmaDisciplinaProfessorDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/turma/{turmaId}")
    public ResponseEntity<List<TurmaDisciplinaProfessorDTO>> buscarPorTurma(@PathVariable Long turmaId) {
        return ResponseEntity.ok(service.buscarPorTurma(turmaId));
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<TurmaDisciplinaProfessorDTO>> buscarPorProfessor(@PathVariable Long professorId) {
        return ResponseEntity.ok(service.buscarPorProfessor(professorId));
    }

    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<List<TurmaDisciplinaProfessorDTO>> buscarPorDisciplina(@PathVariable Long disciplinaId) {
        return ResponseEntity.ok(service.buscarPorDisciplina(disciplinaId));
    }

    @PostMapping
    public ResponseEntity<TurmaDisciplinaProfessorDTO> vincular(@RequestBody TurmaDisciplinaProfessorDTO dto) 
        throws TurmaException, ProfessorException, DisciplinaException {
        return ResponseEntity.ok(service.vincular(dto));
    }

    @DeleteMapping("/{turmaId}/{disciplinaId}/{professorId}")
    public ResponseEntity<Void> desvincular(
        @PathVariable Long turmaId,
        @PathVariable Long disciplinaId,
        @PathVariable Long professorId) {
        service.desvincular(turmaId, disciplinaId, professorId);
        return ResponseEntity.noContent().build();
    }
}
