package com.br.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.validator.constraints.br.CPF;
import com.br.api.exception.InvalidCredentialException;

@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor")
    @Getter private Long id_professor;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "nome_professor")
    @Getter private String nome_professor;

    @NotBlank(message = "CPF é obrigatório")
    @Column(name = "cpf", unique = true)
    @CPF
    @Getter private String cpf;

    @NotBlank(message = "RG é obrigatório")
    @Column(name = "rg", unique = true)
    @Getter private String rg;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(name = "telefone")
    @Getter private String telefone;

    @Column(name = "email")
    @Getter private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Getter private StatusProfessor status;

    @ManyToOne
    @JoinColumn(name = "disciplinas_id_disciplinas")
    @Getter private Disciplina disciplinas;

    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    private List<TurmaDisciplinaProfessor> turmaDisciplinaProfessores = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    @Getter private Endereco endereco;

    // Construtor protegido para JPA
    protected Professor() {}

    // Construtor principal com validações
    public Professor(String nome, String cpf, String rg, String telefone, String email, Disciplina disciplina) {
        this.nome_professor = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.telefone = telefone;
        this.email = email;
        this.disciplinas = disciplina;
        this.status = StatusProfessor.ATIVO;
        
        validarDados();
    }

    // Métodos de acesso para turmaDisciplinaProfessores
    public List<TurmaDisciplinaProfessor> getTurmaDisciplinaProfessores() {
        return new ArrayList<>(turmaDisciplinaProfessores);
    }

    // Comportamentos da entidade
    public void adicionarTurmaDisciplina(TurmaDisciplinaProfessor turmaDisciplinaProfessor) {
        if (turmaDisciplinaProfessor != null && !turmaDisciplinaProfessores.contains(turmaDisciplinaProfessor)) {
            turmaDisciplinaProfessores.add(turmaDisciplinaProfessor);
        }
    }

    public void removerTurmaDisciplina(TurmaDisciplinaProfessor turmaDisciplinaProfessor) {
        turmaDisciplinaProfessores.remove(turmaDisciplinaProfessor);
    }

    public void alterarStatus(StatusProfessor novoStatus) {
        this.status = novoStatus;
    }

    public void alterarDisciplina(Disciplina novaDisciplina) {
        if (novaDisciplina != null) {
            this.disciplinas = novaDisciplina;
        }
    }

    public void definirEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    // Método para atualização de dados básicos (sem dados sensíveis)
    public void atualizarDadosBasicos(String nome, String telefone, String email) {
        this.nome_professor = nome;
        this.telefone = telefone;
        this.email = email;
        
        validarDadosBasicos();
    }

    // Validações encapsuladas
    private void validarDados() {
        if (nome_professor == null || nome_professor.trim().isEmpty()) {
            throw new InvalidCredentialException("Nome do professor é obrigatório");
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
        
        try {
            new com.br.api.valueobject.Telefone(telefone);
        } catch (Exception e) {
            throw new InvalidCredentialException("Telefone inválido: " + telefone);
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
        if (nome_professor == null || nome_professor.trim().isEmpty()) {
            throw new InvalidCredentialException("Nome do professor é obrigatório");
        }
        
        try {
            new com.br.api.valueobject.Telefone(telefone);
        } catch (Exception e) {
            throw new InvalidCredentialException("Telefone inválido: " + telefone);
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
        if (turmaDisciplinaProfessores == null) {
            turmaDisciplinaProfessores = new ArrayList<>();
        }
    }

    public enum StatusProfessor {
        ATIVO, INATIVO
    }
}
