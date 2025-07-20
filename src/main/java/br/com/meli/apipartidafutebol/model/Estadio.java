package br.com.meli.apipartidafutebol.model;


import jakarta.persistence.*;

import java.time.LocalDate;


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
        // Construtores
        public Estadio() {}
        public Estadio(String nome, String cidade, Integer capacidade, Boolean ativo) {
            this.nome = nome;
            this.cidade = cidade;
            this.capacidade = capacidade;
            this.ativo = ativo;
        }

    public Estadio(String maracanã, String rj, int i) {
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

    public void setId(Long id) {
    }
}


