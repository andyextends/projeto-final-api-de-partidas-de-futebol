package br.com.meli.apipartidafutebol.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClubeRequestDto {

  private String nome;

  private String estado;

  private LocalDate dataCriacao;

  private Boolean status;

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao){
        this.dataCriacao = dataCriacao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
