package com.br.api.service;


import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.api.dto.login.LoginCreateDTO;
import com.br.api.dto.login.LoginDTO;
import com.br.api.dto.login.LoginRequestDTO;
import com.br.api.exception.GlobalHandlerException;
import com.br.api.exception.InvalidCredentialException;
import com.br.api.exception.LoginException;
import com.br.api.mapper.LoginMapper;
import com.br.api.model.Login;
import com.br.api.repository.LoginRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final LoginMapper loginMapper;
    private final PasswordEncoder passwordEncoder;

    public LoginDTO criarLogin(LoginCreateDTO dto) throws GlobalHandlerException {
        if(loginRepository.existsByNomeUsuario(dto.nome_usuario())) {
            throw new GlobalHandlerException("Esse nome de usuário já existe!");
        }
        Login login = loginMapper.toEntity(dto);
        login.setSenha(passwordEncoder.encode(login.getSenha()));
        login.setData_criacao(LocalDateTime.now());

        return loginMapper.toDTO(loginRepository.save(login));
    }

    public LoginDTO autenticar(LoginRequestDTO dto) throws InvalidCredentialException {
        Login login = loginRepository.findByNomeUsuario(dto.nome_usuario())
            .orElseThrow(() -> new InvalidCredentialException("Credencias inválidas!"));

        if(!passwordEncoder.matches(dto.senha(), login.getSenha())) {
            throw new InvalidCredentialException("Senha incorreta!");
        }
        return loginMapper.toDTO(loginRepository.save(login));
    }

    public LoginDTO buscarPorId(int id) throws LoginException {
        return loginRepository.findById(id)
            .map(loginMapper::toDTO)
            .orElseThrow(() -> new LoginException("Login não encontrado!"));   
    }


    public void deletarLogin(int id) throws LoginException {
        if(!loginRepository.existsById(id)) {
            throw new LoginException("Login não encontrado!");
        }
        
        loginRepository.deleteById(id);
    }
}
