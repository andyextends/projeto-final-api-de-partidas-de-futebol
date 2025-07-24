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
    @NotBlank(message = "O CEP é obrigatório.")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos numéricos.")
    private String cep;


    // Construtor
    public EstadioRequestDto(String nome, String cidade, Integer capacidade, Boolean ativo, String cep) {
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
        this.ativo = ativo;
        this.cep = cep;
    }
    public EstadioRequestDto() {

    }
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
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
}
