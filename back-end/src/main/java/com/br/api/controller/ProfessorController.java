package com.br.api.controller;


import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.br.api.dto.professor.ProfessorCadastroDTO;
import com.br.api.dto.professor.ProfessorDTO;
import com.br.api.exception.InvalidCredentialException;
import com.br.api.exception.ProfessorNotFoundException;
import com.br.api.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProfessorController {
    
    private final ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> listarProfessores() {
        return ResponseEntity.ok(professorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> buscarPorId(@PathVariable int id) throws ProfessorNotFoundException {
        return ResponseEntity.ok(professorService.buscarProfessorPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> cadastrarProfessor(@Valid @RequestBody ProfessorCadastroDTO dto) throws 
    ProfessorNotFoundException, InvalidCredentialException {
        ProfessorDTO professor = professorService.cadastrarProfessor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(professor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> atualizarProfessor(
        @PathVariable int id,
        @Valid @RequestBody ProfessorCadastroDTO dto
    ) 
    throws ProfessorNotFoundException, InvalidCredentialException {
        return ResponseEntity.ok(professorService.atualizarProfessor(id, dto));
    }

    public ResponseEntity<ProfessorDTO> excluirProfessor(@PathVariable int id) throws
    ProfessorNotFoundException {
        professorService.excluirProfessor(id);
        return ResponseEntity.noContent().build();
    }
}
