package com.br.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.br.api.model.Responsavel;

@Repository
public interface ResponsavelRepository extends JpaRepository<Responsavel, Long>{

    Optional<Responsavel> findByCpf(String cpf);

    Optional<Responsavel> findByRg(String rg);

    @Query("SELECT r FROM Responsavel r JOIN r.alunos a WHERE a.id_aluno = :alunoId")
    Optional<Responsavel> findByAlunoId(int alunoId);

    boolean existsByRg(String rg);

    boolean existsByCpf(String cpf);
}