package br.com.meli.apipartidafutebol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
public class PartidaRequestDto {
    @NotNull(message = "O ID do clube mandante é obrigatório.")
    private Long clubeMandanteId;
    @NotNull(message = "O ID do clube visitante é obrigatório.")
    private Long clubeVisitanteId;
    @NotNull(message = "O ID do estádio é obrigatório.")
    private Long estadioId;
    @NotNull(message = "A data e hora da partida são obrigatórias.")
    @JsonProperty("dataHora")
    private LocalDateTime dataHora;
    private Integer placarMandante;
    private Integer placarVisitante;
    // Getters e Setters
    public Long getClubeMandanteId() {
        return clubeMandanteId;
    }
    public void setClubeMandanteId(Long clubeMandanteId) {
        this.clubeMandanteId = clubeMandanteId;
    }
    public Long getClubeVisitanteId() {
        return clubeVisitanteId;
    }
    public void setClubeVisitanteId(Long clubeVisitanteId) {
        this.clubeVisitanteId = clubeVisitanteId;
    }
    public Long getEstadioId() {
        return estadioId;
    }
    public void setEstadioId(Long estadioId) {
        this.estadioId = estadioId;
    }
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    public Integer getPlacarMandante() {
        return placarMandante;
    }
    public void setPlacarMandante(Integer placarMandante) {
        this.placarMandante = placarMandante;
    }
    public Integer getPlacarVisitante() {
        return placarVisitante;
    }
    public void setPlacarVisitante(Integer placarVisitante) {
        this.placarVisitante = placarVisitante;
    }
}
