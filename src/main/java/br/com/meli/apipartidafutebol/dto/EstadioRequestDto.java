package br.com.meli.apipartidafutebol.dto;

import jakarta.validation.constraints.*;


public class EstadioRequestDto {
    @NotBlank(message = "O nome do estádio é obrigatório.")
    @Size(min = 2, message = "O nome deve ter pelo menos 2 caracteres.")
    private String nome;
    @NotBlank(message = "A cidade é obrigatória.")
    private String cidade;
    @NotNull(message = "A capacidade é obrigatória.")
    @Min(value = 1000, message = "A capacidade mínima deve ser de 1000 pessoas.")
    private Integer capacidade;
    @NotNull(message = "O campo 'ativo' é obrigatório.")
    private Boolean ativo;
    // Getters e Setters
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
}
