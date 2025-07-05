package com.br.api.controller;

import com.br.api.dto.responsaveis.ResponsavelCadastroDTO;
import com.br.api.dto.responsaveis.ResponsavelAtualizacaoDTO;
import com.br.api.dto.responsaveis.ResponsaviesDTO;
import com.br.api.dto.responsaveis.ResponsaveisDetalhesDTO;
import com.br.api.service.ResponsavelService;
import com.br.api.exception.ResponsavelException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import com.br.api.dto.responsaveis.ResponsavelCadastroComLoginDTO;

@RestController
@RequestMapping("/responsaveis")
@RequiredArgsConstructor
public class ResponsavelController {
    private final ResponsavelService responsavelService;

    @PostMapping
    public ResponseEntity<ResponsaviesDTO> cadastrar(@Valid @RequestBody ResponsavelCadastroDTO dto) throws ResponsavelException {
        return ResponseEntity.status(HttpStatus.CREATED).body(responsavelService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ResponsaviesDTO>> listarTodos() {
        return ResponseEntity.ok(responsavelService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsaveisDetalhesDTO> buscarPorId(@PathVariable Long id) throws ResponsavelException {
        return ResponseEntity.ok(responsavelService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsaviesDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ResponsavelAtualizacaoDTO dto) throws ResponsavelException {
        return ResponseEntity.ok(responsavelService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws ResponsavelException {
        responsavelService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarComLogin(@RequestBody ResponsavelCadastroComLoginDTO dto) throws ResponsavelException, com.br.api.exception.InvalidCredentialException {
        try {
            responsavelService.cadastrarComLogin(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Responsável cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 