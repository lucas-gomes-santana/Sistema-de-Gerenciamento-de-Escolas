package com.br.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.hibernate.validator.constraints.br.CPF;
import com.br.api.exception.InvalidCredentialException;

@Entity
@Table(name = "aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno")
    @Getter private Long id_aluno;

    @NotBlank
    @Column(name = "nome_aluno")
    @Getter private String nome_aluno;

    @NotBlank
    @Column(name = "cpf", unique = true, nullable = false)
    @CPF
    @Getter private String cpf;

    @NotBlank
    @Column(name = "rg", unique = true, nullable = false)
    @Getter private String rg;

    @Column(name = "telefone")
    @Getter private String telefone;

    @Column(name = "email")
    @Getter private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Getter private StatusAluno status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_turma")
    @Getter private Turma turma;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "aluno_responsavel",
        joinColumns = @JoinColumn(name = "id_aluno"),
        inverseJoinColumns = @JoinColumn(name = "id_responsaveis"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_aluno", "id_responsaveis"})
    )
    private List<Responsavel> responsaveis = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    @Getter private Endereco endereco;

    // Construtor protegido para JPA
    protected Aluno() {}

    // Construtor principal com validações
    public Aluno(String nome, String cpf, String rg, String telefone, String email, Turma turma) {
        this.nome_aluno = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.telefone = telefone;
        this.email = email;
        this.turma = turma;
        this.status = StatusAluno.PRESENTE;
        
        validarDados();
    }

    // Métodos de acesso para responsáveis
    public List<Responsavel> getResponsaveis() {
        return Collections.unmodifiableList(responsaveis);
    }

    // Comportamentos da entidade
    public void adicionarResponsavel(Responsavel responsavel) {
        if (responsavel != null && !responsaveis.contains(responsavel)) {
            responsaveis.add(responsavel);
        }
    }

    public void removerResponsavel(Responsavel responsavel) {
        responsaveis.remove(responsavel);
    }

    public void alterarStatus(StatusAluno novoStatus) {
        this.status = novoStatus;
    }

    public void alterarTurma(Turma novaTurma) {
        if (novaTurma != null) {
            this.turma = novaTurma;
        }
    }

    public void definirEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    // Método para atualização de dados básicos (sem dados sensíveis)
    public void atualizarDadosBasicos(String nome, String telefone, String email) {
        this.nome_aluno = nome;
        this.telefone = telefone;
        this.email = email;
        
        validarDadosBasicos();
    }

    // Validações encapsuladas
    private void validarDados() {
        if (nome_aluno == null || nome_aluno.trim().isEmpty()) {
            throw new InvalidCredentialException("Nome do aluno é obrigatório");
        }
        
        try {
            new com.br.api.valueobject.CPF(cpf);
        } catch (Exception e) {
            throw new InvalidCredentialException("CPF inválido: " + cpf);
        }
        
        try {
            new com.br.api.valueobject.RG(rg);
        } catch (Exception e) {
            throw new InvalidCredentialException("RG inválido: " + rg);
        }
        
        if (telefone != null) {
            try {
                new com.br.api.valueobject.Telefone(telefone);
            } catch (Exception e) {
                throw new InvalidCredentialException("Telefone inválido: " + telefone);
            }
        }
        
        if (email != null) {
            try {
                new com.br.api.valueobject.Email(email);
            } catch (Exception e) {
                throw new InvalidCredentialException("Email inválido: " + email);
            }
        }
    }

    private void validarDadosBasicos() {
        if (nome_aluno == null || nome_aluno.trim().isEmpty()) {
            throw new InvalidCredentialException("Nome do aluno é obrigatório");
        }
        
        if (telefone != null) {
            try {
                new com.br.api.valueobject.Telefone(telefone);
            } catch (Exception e) {
                throw new InvalidCredentialException("Telefone inválido: " + telefone);
            }
        }
        
        if (email != null) {
            try {
                new com.br.api.valueobject.Email(email);
            } catch (Exception e) {
                throw new InvalidCredentialException("Email inválido: " + email);
            }
        }
    }

    // Métodos para JPA (protegidos)
    @PostLoad
    protected void onLoad() {
        if (responsaveis == null) {
            responsaveis = new ArrayList<>();
        }
    }

    public enum StatusAluno {
        PRESENTE, 
        POUCO_PRESENTE, 
        AUSENTE
    }
}
