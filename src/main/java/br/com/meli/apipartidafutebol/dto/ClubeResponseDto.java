package br.com.meli.apipartidafutebol.dto;

import br.com.meli.apipartidafutebol.model.Clube;
import java.time.LocalDate;
public class ClubeResponseDto {
    private Long id;
    private String nome;
    private String siglaEstado;
    private LocalDate dataCriacao;
    private Boolean ativo;
    public ClubeResponseDto(Long id, String nome, String siglaEstado, LocalDate dataCriacao, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.siglaEstado = siglaEstado;
        this.dataCriacao = dataCriacao;
        this.ativo = ativo;
    }
    // Construtor de conversão direto de entidade
    public ClubeResponseDto(Clube clube) {
        this(
                clube.getId(),
                clube.getNome(),
                clube.getSiglaEstado(),
                clube.getDataCriacao(),
                clube.getAtivo()
        );
    }
    // Getters
    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getSiglaEstado() {
        return siglaEstado;
    }
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    public Boolean getAtivo() {
        return ativo;
    }
}










