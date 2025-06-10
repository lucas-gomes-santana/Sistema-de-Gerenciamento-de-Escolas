package com.br.api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.br.api.dto.professor.ProfessorCadastroDTO;
import com.br.api.dto.professor.ProfessorDTO;
import com.br.api.exception.InvalidCredentialException;
import com.br.api.exception.ProfessorNotFoundException;
import com.br.api.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/professores")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> listarTodos() {
        return ResponseEntity.ok(professorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(professorService.buscarPorId(id));
        } catch (ProfessorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> cadastrar(@Valid @RequestBody ProfessorCadastroDTO dto) throws InvalidCredentialException {
        try {
            ProfessorDTO professor = professorService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(professor);
        } 
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProfessorCadastroDTO dto) throws InvalidCredentialException {
        try {
            return ResponseEntity.ok(professorService.atualizar(id, dto));
        } 
        catch (ProfessorNotFoundException e) {
            return ResponseEntity.notFound().build();
        } 
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            professorService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (ProfessorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
