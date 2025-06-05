package com.br.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.br.api.model.Turma;


@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer> {
    List<Turma> findByTurno(String turno);

    @Query("SELECT t FROM Turma t JOIN t.professores p WHERE p.id_professor = :professorId")
    List<Turma> findByProfessorId(int professorId);
}
