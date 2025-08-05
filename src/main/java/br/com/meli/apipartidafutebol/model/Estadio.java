package br.com.meli.apipartidafutebol.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public Estadio(String nome, String cidade, Integer capacidade, Boolean ativo,String cep) {
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
        this.ativo = ativo;
        this.cep = cep;
    }

}


