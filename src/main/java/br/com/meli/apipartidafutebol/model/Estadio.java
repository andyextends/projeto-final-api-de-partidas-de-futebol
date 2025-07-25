package br.com.meli.apipartidafutebol.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estadios")
public class Estadio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 100)
    private String cidade;
    @Column(nullable = false)
    private Integer capacidade;
    @Column(nullable = false)
    private Boolean ativo;
    @Column(length = 9)
    private String cep;
    @Column(length = 100)
    private String logradouro;
    @Column(length = 100)
    private String bairro;
    @Column(length = 2)
    private String uf;

    // Construtores
    public Estadio() {
    }

    public Estadio(String nome, String cidade, Integer capacidade, Boolean ativo,String cep) {
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
        this.ativo = ativo;
        this.cep = cep;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}


