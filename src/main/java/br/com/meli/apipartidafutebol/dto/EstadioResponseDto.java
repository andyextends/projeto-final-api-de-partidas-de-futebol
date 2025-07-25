package br.com.meli.apipartidafutebol.dto;

import br.com.meli.apipartidafutebol.model.Estadio;

import java.util.UUID;

public class EstadioResponseDto {
    private Long id;
    private String nome;
    private String cidade;
    private Integer capacidade;
    private Boolean ativo;
    private String cep;

    // Construtor completo
    public EstadioResponseDto(Long id, String nome, String cidade, Integer capacidade, Boolean ativo, String cep) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
        this.ativo = ativo;
        this.cep = cep;
    }
    // Construtor que recebe a entidade Estadio
    public EstadioResponseDto(Estadio estadio) {
        this(
                estadio.getId(),
                estadio.getNome(),
                estadio.getCidade(),
                estadio.getCapacidade(),
                estadio.getAtivo(),
                estadio.getCep()
        );
    }

    public EstadioResponseDto() {

    }

    // Getters


    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

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
}
