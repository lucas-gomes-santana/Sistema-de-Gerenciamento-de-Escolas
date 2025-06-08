package com.br.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

@Data
@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor")
    private Long id_professor;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "nome_professor")
    private String nome_professor;

    @NotBlank(message = "CPF é obrigatório")
    @Column(name = "cpf", unique = true)
    @CPF
    private String cpf;

    @NotBlank(message = "RG é obrigatório")
    @Column(name = "rg", unique = true)
    private String rg;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Telefone inválido")
    @Column(name = "telefone")
    private String telefone;

    @Email(message = "Email inválido")
    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "disciplinas_id_disciplinas")
    private Disciplina disciplinas;

    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    private List<TurmaDisciplinaProfessor> turmaDisciplinaProfessores;

    public enum StatusProfessor {
        ATIVO, INATIVO
    }
}
