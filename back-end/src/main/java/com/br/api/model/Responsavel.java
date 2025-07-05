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
@Table(name = "responsaveis")
public class Responsavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_responsaveis")
    @Getter private Long id_responsavies;

    @Column(name = "nome_responsavel")
    @NotBlank    
    @Getter private String nome_responsavel;

    @CPF
    @Column(name = "cpf")
    @NotBlank
    @Getter private String cpf;

    @Column(name = "rg")
    @NotBlank
    @Getter private String rg;

    @Column(name = "telefone")
    @Getter private String telefone;

    @ManyToMany(mappedBy = "responsaveis", fetch = FetchType.LAZY)
    private List<Aluno> alunos = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    @Getter private Endereco endereco;

    // Construtor protegido para JPA
    protected Responsavel() {}

    // Construtor principal com validações
    public Responsavel(String nome, String cpf, String rg, String telefone) {
        this.nome_responsavel = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.telefone = telefone;
        
        validarDados();
    }

    // Métodos de acesso para alunos
    public List<Aluno> getAlunos() {
        return Collections.unmodifiableList(alunos);
    }

    // Comportamentos da entidade
    public void adicionarAluno(Aluno aluno) {
        if (aluno != null && !alunos.contains(aluno)) {
            alunos.add(aluno);
        }
    }

    public void removerAluno(Aluno aluno) {
        alunos.remove(aluno);
    }

    public void definirEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    // Método para atualização de dados básicos (sem dados sensíveis)
    public void atualizarDadosBasicos(String nome, String telefone) {
        this.nome_responsavel = nome;
        this.telefone = telefone;
        
        validarDadosBasicos();
    }

    // Validações encapsuladas
    private void validarDados() {
        if (nome_responsavel == null || nome_responsavel.trim().isEmpty()) {
            throw new InvalidCredentialException("Nome do responsável é obrigatório");
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
    }

    private void validarDadosBasicos() {
        if (nome_responsavel == null || nome_responsavel.trim().isEmpty()) {
            throw new InvalidCredentialException("Nome do responsável é obrigatório");
        }
        
        if (telefone != null) {
            try {
                new com.br.api.valueobject.Telefone(telefone);
            } catch (Exception e) {
                throw new InvalidCredentialException("Telefone inválido: " + telefone);
            }
        }
    }

    // Métodos para JPA (protegidos)
    @PostLoad
    protected void onLoad() {
        if (alunos == null) {
            alunos = new ArrayList<>();
        }
    }
}
