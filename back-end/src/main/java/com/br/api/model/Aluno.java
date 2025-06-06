package com.br.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "aluno")
@Getter
@Setter
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno")
    private Long id_aluno;

    @NotBlank
    @Column(name = "nome_aluno")
    private String nome_aluno;

    @NotBlank
    @Column(name = "cpf", unique = true, nullable = false)
    @CPF
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF inválido")
    private String cpf;

    @NotBlank
    @Column(name = "rg", unique = true, nullable = false)
    @Pattern(regexp = "^(\\d{1,2}\\.?\\d{3}\\.?\\d{3}-?[\\dX]{1,2}|\\d{8,10}|[\\dX]{7,10})$", message = "RG inválido")
    private String rg;

    @Column(name = "telefone")
    private String telefone;

    @Email
    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAluno status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_turma")
    private Turma turma;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "aluno_responsavel",
        joinColumns = @JoinColumn(name = "id_aluno"),
        inverseJoinColumns = @JoinColumn(name = "id_responsaveis"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_aluno", "id_responsaveis"})
    )
    private List<Responsavel> responsaveis;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    public enum StatusAluno {
        PRESENTE, 
        POUCO_PRESENTE, 
        AUSENTE
    }
}
