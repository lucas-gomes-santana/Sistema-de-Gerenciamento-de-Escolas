package com.br.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.br.api.model.Professor;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long>{
    
    @Query("SELECT p FROM Professor p WHERE p.cpf = :cpf")
    Optional<Professor> findByCpf(@Param("cpf") String cpf);

    @Query("SELECT p FROM Professor p WHERE p.rg = :rg")
    Optional<Professor> findByRg(@Param("rg") String rg);

    @Query("SELECT p FROM Professor p WHERE p.email = :email")
    Optional<Professor> findByEmail(@Param("email") String email);

    @Query("SELECT p FROM Professor p JOIN p.turmaDisciplinaProfessores tdp WHERE tdp.disciplina.id_disciplinas = :disciplinaId")
    List<Professor> findByDisciplinaId(@Param("disciplinaId") Long disciplinaId);

    boolean existsByCpf(String cpf);

    boolean existsByRg(String rg);
}