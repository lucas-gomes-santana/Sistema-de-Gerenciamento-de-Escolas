package com.br.api.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import com.br.api.exception.InvalidCredentialException;

@Entity
@Table(name = "turma")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turma")
    @Getter private Long id_turma;

    @NotBlank
    @Column(name = "nome_turma")
    @Getter private String nome_turma;

    @NotBlank
    @Column(name = "turno")
    @Getter private String turno;

    @OneToMany(mappedBy = "turma")
    private List<Aluno> alunos = new ArrayList<>();

    @OneToMany(mappedBy = "turma")
    private List<TurmaDisciplinaProfessor> turmaDisciplinaProfessores = new ArrayList<>();

    // Construtor protegido para JPA
    protected Turma() {}

    // Construtor principal com validações
    public Turma(String nome, String turno) {
        this.nome_turma = nome;
        this.turno = turno;
        
        validarDados();
    }

    // Métodos de acesso para listas
    public List<Aluno> getAlunos() {
        return Collections.unmodifiableList(alunos);
    }

    public List<TurmaDisciplinaProfessor> getTurmaDisciplinaProfessores() {
        return Collections.unmodifiableList(turmaDisciplinaProfessores);
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

    public void adicionarTurmaDisciplinaProfessor(TurmaDisciplinaProfessor turmaDisciplinaProfessor) {
        if (turmaDisciplinaProfessor != null && !turmaDisciplinaProfessores.contains(turmaDisciplinaProfessor)) {
            turmaDisciplinaProfessores.add(turmaDisciplinaProfessor);
        }
    }

    public void removerTurmaDisciplinaProfessor(TurmaDisciplinaProfessor turmaDisciplinaProfessor) {
        turmaDisciplinaProfessores.remove(turmaDisciplinaProfessor);
    }

    // Método para atualização de dados
    public void atualizarDados(String nome, String turno) {
        this.nome_turma = nome;
        this.turno = turno;
        
        validarDados();
    }

    // Validações encapsuladas
    private void validarDados() {
        if (nome_turma == null || nome_turma.trim().isEmpty()) {
            throw new InvalidCredentialException("Nome da turma é obrigatório");
        }
        
        if (turno == null || turno.trim().isEmpty()) {
            throw new InvalidCredentialException("Turno é obrigatório");
        }
        
        // Validar se o turno é válido
        String turnoUpper = turno.toUpperCase();
        if (!turnoUpper.equals("MATUTINO") && !turnoUpper.equals("VESPERTINO") && !turnoUpper.equals("NOTURNO")) {
            throw new InvalidCredentialException("Turno inválido. Deve ser MATUTINO, VESPERTINO ou NOTURNO");
        }
    }

    // Métodos para JPA (protegidos)
    @PostLoad
    protected void onLoad() {
        if (alunos == null) {
            alunos = new ArrayList<>();
        }
        if (turmaDisciplinaProfessores == null) {
            turmaDisciplinaProfessores = new ArrayList<>();
        }
    }

    // Métodos de negócio
    public int getQuantidadeAlunos() {
        return alunos.size();
    }

    public boolean temAlunos() {
        return !alunos.isEmpty();
    }

    public boolean temDisciplinas() {
        return !turmaDisciplinaProfessores.isEmpty();
    }

    public int getQuantidadeDisciplinas() {
        return turmaDisciplinaProfessores.size();
    }
}
