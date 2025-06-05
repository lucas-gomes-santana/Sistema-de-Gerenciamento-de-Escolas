package com.br.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter
@Setter
public class Endereco {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    @Id
    private int id_endereco;

    @Column(name = "nome_rua")
    private String nome_rua;

    @Column(name = "nome_bairro")
    private String nome_bairro;

    @NotBlank
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP inválido")
    @Column(name = "cep")
    private String cep;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "tipo_entidade")
    @Enumerated(EnumType.STRING)
    private TipoEntidade tipo_entidade;

    @Column(name = "id_entidade")
    private int id_entidade;

    public enum TipoEntidade {
        PROFESSOR,
        ADMIN,
        ALUNO
    }
}
