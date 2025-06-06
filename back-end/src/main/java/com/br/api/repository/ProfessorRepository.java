package com.br.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.br.api.model.Professor;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long>{
    
    Optional<Professor> findByCpf(String cpf);

    Optional<Professor> findByRg(String rg);

    @Query("SELECT p FROM Professor p WHERE p.disciplinas = :disciplinas")
    List<Professor> findByDisciplinaId(int disciplinaId); 

    boolean existsByCpf(String cpf);

    boolean existsByRg(String rg);
}