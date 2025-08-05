package br.com.meli.apipartidafutebol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidaRequestDto {
    @NotNull(message = "O ID do clube mandante é obrigatório.")
    private Long clubeMandanteId;
    @NotNull(message = "O ID do clube visitante é obrigatório.")
    private Long clubeVisitanteId;
    @NotNull(message = "O ID do estádio é obrigatório.")
    private Long estadioId;
    @NotNull(message = "A data e hora da partida são obrigatórias.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("dataHora")
    private LocalDateTime dataHora;
    private Integer placarMandante;
    private Integer placarVisitante;


}
