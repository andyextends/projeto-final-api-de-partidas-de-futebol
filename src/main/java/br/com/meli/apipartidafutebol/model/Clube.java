package br.com.meli.apipartidafutebol.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "clubes")
public class Clube {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 2)
    private String siglaEstado;
    @Column(nullable = false)
    private LocalDate dataCriacao;
    @Column(nullable = false)
    private Boolean ativo;
    // Construtor padrão
    public Clube() {}
    // Construtor completo
    public Clube(String nome, String siglaEstado, LocalDate dataCriacao, Boolean ativo) {
        this.nome = nome;
        this.siglaEstado = siglaEstado;
        this.dataCriacao = dataCriacao;
        this.ativo = ativo;
    }
    // Getters e Setters
    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSiglaEstado() {
        return siglaEstado;
    }
    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

}
