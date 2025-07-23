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



    public ClubeResponseDto(Clube clube) {
        this.id = clube.getId();
        this.nome = clube.getNome();
        this.siglaEstado = clube.getSiglaEstado();
        this.dataCriacao = clube.getDataCriacao();
        this.ativo = clube.getAtivo();
    }

    public ClubeResponseDto() {

    }


    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
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

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }
}


  












