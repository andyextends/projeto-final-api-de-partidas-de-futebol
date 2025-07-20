package br.com.meli.apipartidafutebol.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;


public class ClubeRequestDto {
    @NotBlank(message = "O nome do clube é obrigatório.")
    @Size(min = 2, message = "O nome deve ter pelo menos 2 letras.")
    private String nome;
    @NotBlank(message = "A sigla do estado é obrigatória.")
    @Pattern(regexp = "^[A-Z]{2}$", message = "A sigla do estado deve conter exatamente 2 letras maiúsculas.")
    private String siglaEstado;
    @NotNull(message = "A data de criação é obrigatória.")
    @PastOrPresent(message = "A data de criação não pode ser no futuro.")
    private LocalDate dataCriacao;
    @NotNull(message = "O campo 'ativo' é obrigatório.")
    private Boolean ativo;

    public ClubeRequestDto(Boolean ativo, LocalDate dataCriacao, String nome, String siglaEstado) {
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
        this.nome = nome;
        this.siglaEstado = siglaEstado;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

