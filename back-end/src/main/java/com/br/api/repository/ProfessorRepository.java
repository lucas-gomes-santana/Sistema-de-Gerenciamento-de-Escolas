package com.br.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.br.api.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer>{
}