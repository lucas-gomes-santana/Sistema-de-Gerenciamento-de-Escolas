package com.br.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.dto.turma.TurmaDTO;
import com.br.api.exception.TurmaNotFoundException;
import com.br.api.dto.turma.TurmaCadastroDTO;
import com.br.api.service.TurmaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TurmaController {

    private final TurmaService turmaService;

    @PostMapping
    public ResponseEntity<TurmaDTO> criar(@Valid @RequestBody TurmaCadastroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(turmaService.criarTurma(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaDTO> buscarPorId(@PathVariable int id) throws TurmaNotFoundException {
        return ResponseEntity.ok(turmaService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TurmaDTO>> listarTodas() {
        return ResponseEntity.ok(turmaService.listarTodas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> atualizar(@PathVariable int id, @Valid @RequestBody TurmaCadastroDTO dto) 
    throws TurmaNotFoundException {
        return ResponseEntity.ok(turmaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTurma(@PathVariable int id) throws TurmaNotFoundException {
        turmaService.deletarTurma(id);
        return ResponseEntity.noContent().build();
    }
}
