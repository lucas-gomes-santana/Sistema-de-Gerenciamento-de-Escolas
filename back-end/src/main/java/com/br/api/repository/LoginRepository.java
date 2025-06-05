package com.br.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.api.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {
    Optional<Login> findByNomeUsuario(String nomeUsuario);
    boolean existsByNomeUsuario(String nomeUsuario);
}
