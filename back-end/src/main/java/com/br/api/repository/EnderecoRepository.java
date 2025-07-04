package com.br.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.br.api.model.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
} 