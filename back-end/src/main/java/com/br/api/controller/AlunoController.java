package com.br.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.service.AlunoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.br.api.dto.aluno.*;
import com.br.api.exception.AlunoException;
import com.br.api.exception.TurmaException;
import com.br.api.exception.InvalidCredentialException;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> listarAlunos() {
        return ResponseEntity.ok(alunoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDetalhesDTO> buscarPorId(@PathVariable Long id) throws AlunoException {
        return ResponseEntity.ok(alunoService.buscarAlunoPorId(id));
    }

    @GetMapping("/turma/{turmaId}")
    public ResponseEntity<List<AlunoDTO>> buscarPorTurma(@PathVariable Long id_turma) {
        return ResponseEntity.ok(alunoService.buscarAlunosPorTurma(id_turma));
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> cadastrarAluno(@Valid @RequestBody AlunoCadastroDTO dto) throws 
    AlunoException, InvalidCredentialException {
        AlunoDTO aluno = alunoService.cadastrarAluno(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(aluno);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarAlunoComLogin(@RequestBody CadastroAlunoComLoginDTO dto) 
    throws AlunoException, InvalidCredentialException {
        try {
            alunoService.cadastrarAlunoComLogin(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Aluno e login cadastrados com sucesso!");
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizarAluno(
        @PathVariable Long id,
        @Valid @RequestBody AlunoAtualizacaoDTO dto
    )
    throws AlunoException, TurmaException, InvalidCredentialException {
        return ResponseEntity.ok(alunoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAluno(@PathVariable Long id) throws 
    AlunoException {
        alunoService.excluirAluno(id);
        return ResponseEntity.noContent().build();
    }
}
