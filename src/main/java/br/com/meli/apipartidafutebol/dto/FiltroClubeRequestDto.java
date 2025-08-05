package br.com.meli.apipartidafutebol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroClubeRequestDto {
    private String nome;
    private String siglaEstado;
    private Boolean ativo;
    private LocalDate dataCriacaoInicial;
    private LocalDate dataCriacaoFinal;

}
