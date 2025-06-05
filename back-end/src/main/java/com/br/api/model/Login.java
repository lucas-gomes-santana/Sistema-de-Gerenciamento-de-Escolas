package com.br.api.model;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "login")
@Getter
@Setter
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_login")
    private int id_login;

    @NotBlank(message = "O nome do usuário é obrigatório")
    @Column(name = "nome_usuario", unique = true, nullable = false)
    private String nome_usuario;

    @NotBlank(message = "O tipo de usuário é obrigatório")
    @Column(name = "tipo_usuario")
    private TipoUsuario tipo_usuario;

    @Column(name = "id_usuario")
    private int id_usuario;

    @NotBlank(message = "A senha é obrigatória")
    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "data_criacao")
    private LocalDateTime data_criacao;

    public enum TipoUsuario {
        ADMIN,
        PROFESSOR,
        ALUNO,
        RESPONSAVEL,
    }
}