package br.com.meli.apipartidafutebol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroPartidaRequestDto {
    private Long clubeMandanteId;
    private Long clubeVisitanteId;
    private Long estadioId;
    private LocalDate data;
    private Boolean goleada;
    private String tipoClube; // "MANDANTE" ou "VISITANTE"

}











