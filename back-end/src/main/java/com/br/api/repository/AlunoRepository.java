package com.br.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.br.api.model.Aluno;
import java.util.Optional;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> { 

    @Query("SELECT a FROM Aluno a WHERE a.turma.id_turma = :turmaId")
    List<Aluno> findByTurmaId(@Param("turmaId") Long turmaId);

    @Query("SELECT a FROM Aluno a WHERE a.cpf = :cpf")
    Optional<Aluno> findByCpf(@Param("cpf") String cpf);

    @Query("SELECT a FROM Aluno a WHERE a.rg = :rg")
    Optional<Aluno> findByRg(@Param("rg") String rg);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Aluno a WHERE a.cpf = :cpf")
    boolean existsByCpf(@Param("cpf") String cpf);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Aluno a WHERE a.rg = :rg")
    boolean existsByRg(@Param("rg") String rg);
}
