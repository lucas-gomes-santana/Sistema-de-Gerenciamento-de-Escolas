package com.br.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.api.model.Disciplina;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    @Query("SELECT d FROM Disciplina d WHERE d.nome_disciplina = :nome_disciplina")
    Optional<Disciplina> findByNome_disciplina(@Param("nome_disciplina") String nome_disciplina);

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Disciplina d WHERE d.nome_disciplina = :nome_disciplina")
    boolean existsByNome_disciplina(@Param("nome_disciplina") String nome_disciplina);
}
