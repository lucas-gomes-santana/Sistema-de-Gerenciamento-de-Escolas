package com.br.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.br.api.model.Aluno;
import java.util.Optional;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> { 

    List<Aluno> findByTurmaId(int turmaId);

    Optional<Aluno> findByRg(String rg);

    Optional<Aluno> findByCPF(String cpf);

    boolean existsByCpf(String cpf);

    boolean existsByRg(String rg);
}
