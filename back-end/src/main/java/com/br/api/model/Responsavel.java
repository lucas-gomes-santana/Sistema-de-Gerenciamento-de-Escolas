package com.br.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import java.util.List;

@Entity
@Table(name = "responsaveis")
@Getter
@Setter
public class Responsavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_responsaveis")
    private Long id_responsavies;

    @Column(name = "nome_responsavel")
    @NotBlank    
    private String nome_responsavel;

    @CPF
    @Column(name = "cpf")
    @NotBlank
    private String cpf;

    @Column(name = "rg")
    @NotBlank
    private String rg;

    @Column(name = "telefone")
    private String telefone;

    @ManyToMany(mappedBy = "responsaveis", fetch = FetchType.LAZY)
    private List<Aluno> alunos;
}
