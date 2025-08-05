package br.com.meli.apipartidafutebol.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
