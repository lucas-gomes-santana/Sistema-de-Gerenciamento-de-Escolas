package com.br.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.api.model.TurmaDisciplinaProfessor;

@Repository
public interface TurmaDisciplinaProfessorRepository extends JpaRepository<TurmaDisciplinaProfessor, Long> {
    @Query("SELECT tdp FROM TurmaDisciplinaProfessor tdp WHERE tdp.turma.id_turma = :turmaId")
    List<TurmaDisciplinaProfessor> findByTurmaId(@Param("turmaId") Long turmaId);

    @Query("SELECT tdp FROM TurmaDisciplinaProfessor tdp WHERE tdp.professor.id_professor = :professorId")
    List<TurmaDisciplinaProfessor> findByProfessorId(@Param("professorId") Long professorId);

    @Query("SELECT tdp FROM TurmaDisciplinaProfessor tdp WHERE tdp.disciplina.id_disciplinas = :disciplinaId")
    List<TurmaDisciplinaProfessor> findByDisciplinaId(@Param("disciplinaId") Long disciplinaId);
} 