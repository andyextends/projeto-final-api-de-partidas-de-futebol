package br.com.meli.apipartidafutebol.dto;

import br.com.meli.apipartidafutebol.model.Estadio;
public class EstadioResponseDto {
    private Long id;
    private String nome;
    private String cidade;
    private Integer capacidade;
    private Boolean ativo;

    // Construtor completo
    public EstadioResponseDto(Long id, String nome, String cidade, Integer capacidade, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
        this.ativo = ativo;
    }
    // Construtor que recebe a entidade Estadio
    public EstadioResponseDto(Estadio estadio) {
        this(
                estadio.getId(),
                estadio.getNome(),
                estadio.getCidade(),
                estadio.getCapacidade(),
                estadio.getAtivo()
        );
    }
    // Getters
    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getCidade() {
        return cidade;
    }
    public Integer getCapacidade() {
        return capacidade;
    }
    public Boolean getAtivo() {
        return ativo;
    }


}
