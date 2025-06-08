package com.br.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.dto.login.LoginCreateDTO;
import com.br.api.dto.login.LoginDTO;
import com.br.api.dto.login.LoginRequestDTO;
import com.br.api.exception.GlobalHandlerException;
import com.br.api.exception.InvalidCredentialException;
import com.br.api.exception.LoginException;
import com.br.api.service.LoginService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LoginController {
    
    private final LoginService loginService;

    @PostMapping("/autenticar")
    public ResponseEntity<LoginDTO> autenticar(@Valid @RequestBody LoginRequestDTO dto) 
    throws InvalidCredentialException {
        return ResponseEntity.ok(loginService.autenticar(dto));
    }

    @PostMapping
    public ResponseEntity<LoginDTO> criarLogin(@Valid @RequestBody LoginCreateDTO dto) 
    throws GlobalHandlerException {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(loginService.criarLogin(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoginDTO> buscarPorId(@PathVariable Long id) 
    throws LoginException {
        return ResponseEntity.ok(loginService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLogin(@PathVariable Long id) throws LoginException {
        loginService.deletarLogin(id);
        return ResponseEntity.noContent().build();
    }
}
