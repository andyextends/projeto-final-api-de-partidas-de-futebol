package br.com.meli.apipartidafutebol.dto;

import java.time.LocalDate;

public class FiltroClubeRequestDto {
    private String nome;
    private String siglaEstado;
    private Boolean ativo;
    private LocalDate dataCriacaoInicial;
    private LocalDate dataCriacaoFinal;


    public FiltroClubeRequestDto(String nome, String siglaEstado, Boolean ativo) {
        this.nome = nome;
        this.siglaEstado = siglaEstado;
        this.ativo = ativo;

    }

    public FiltroClubeRequestDto() {

    }


    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDate getDataCriacaoFinal() {
        return dataCriacaoFinal;
    }

    public void setDataCriacaoFinal(LocalDate dataCriacaoFinal) {
        this.dataCriacaoFinal = dataCriacaoFinal;
    }

    public LocalDate getDataCriacaoInicial() {
        return dataCriacaoInicial;
    }

    public void setDataCriacaoInicial(LocalDate dataCriacaoInicial) {
        this.dataCriacaoInicial = dataCriacaoInicial;
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
