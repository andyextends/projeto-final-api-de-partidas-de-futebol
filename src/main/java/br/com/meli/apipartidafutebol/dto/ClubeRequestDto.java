package br.com.meli.apipartidafutebol.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
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


}

