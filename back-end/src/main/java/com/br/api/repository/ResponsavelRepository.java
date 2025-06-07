package com.br.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.br.api.model.Responsavel;

@Repository
public interface ResponsavelRepository extends JpaRepository<Responsavel, Long>{

    @Query("SELECT r FROM Responsavel r WHERE r.cpf = :cpf")
    Optional<Responsavel> findByCpf(@Param("cpf") String cpf);

    @Query("SELECT r FROM Responsavel r WHERE r.rg = :rg")
    Optional<Responsavel> findByRg(@Param("rg") String rg);

    @Query("SELECT r FROM Responsavel r JOIN r.alunos a WHERE a.id_aluno = :alunoId")
    Optional<Responsavel> findByAlunoId(@Param("alunoId") Long alunoId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Responsavel r WHERE r.cpf = :cpf")
    boolean existsByCpf(@Param("cpf") String cpf);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Responsavel r WHERE r.rg = :rg")
    boolean existsByRg(@Param("rg") String rg);
}