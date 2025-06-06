package com.br.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "professor")
@Getter
@Setter
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor")
    private Long id_professor;

    @NotBlank
    @Column(name = "nome_professor")
    private String nome_professor;

    @NotBlank
    @Column(name = "cpf", unique = true, nullable = false)
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF inválido")
    @CPF
    private String cpf;

    @NotBlank
    @Column(name = "rg", unique = true, nullable = false)
    @Pattern(regexp = "^(\\d{1,2}\\.?\\d{3}\\.?\\d{3}-?[\\dX]{1,2}|\\d{8,10}|[\\dX]{7,10})$", message = "RG inválido")
    private String rg;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Email
    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusProfessor status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @ManyToMany(fetch =  FetchType.LAZY)
    @JoinTable(
        name = "turma_disciplina_professor",
        joinColumns = @JoinColumn(name = "id_professor"),
        inverseJoinColumns = @JoinColumn(name = "id_disciplinas")
    )
    private List<Disciplina> disciplinas;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "id_disciplina_professor",
        joinColumns = @JoinColumn(name = "id_professor"),
        inverseJoinColumns = @JoinColumn(name = "id_turma")
    )
    private Set<Turma> turmas;

    public enum StatusProfessor {
        ATIVO, INATIVO
    }
}
