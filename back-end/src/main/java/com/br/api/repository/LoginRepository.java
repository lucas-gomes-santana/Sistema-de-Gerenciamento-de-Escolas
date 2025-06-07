package com.br.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.api.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    @Query("SELECT l FROM Login l WHERE l.nome_usuario = :nome_usuario")
    Optional<Login> findByNome_usuario(@Param("nome_usuario") String nome_usuario);
    
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Login l WHERE l.nome_usuario = :nome_usuario")
    boolean existsByNome_usuario(@Param("nome_usuario") String nome_usuario);
}
