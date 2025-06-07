package com.br.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.br.api.model.Turma;


@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findByTurno(String turno);

    @Query("SELECT t FROM Turma t WHERE t.nome_turma = :nome_turma")
    Optional<Turma> findByNome_turma(@Param("nome_turma") String nome_turma);

    @Query("SELECT DISTINCT t FROM Turma t JOIN t.turmaDisciplinaProfessores tdp WHERE tdp.professor.id_professor = :professorId")
    List<Turma> findByProfessorId(@Param("professorId") Long professorId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Turma t WHERE t.nome_turma = :nome_turma")
    boolean existsByNome_turma(@Param("nome_turma") String nome_turma);
}
