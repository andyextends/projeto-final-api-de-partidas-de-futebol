package br.com.meli.apipartidafutebol.dto;

import java.time.LocalDate;

public class FiltroPartidaRequestDto {
    private Long clubeMandanteId;
    private Long clubeVisitanteId;
    private Long estadioId;
    private LocalDate data;
    private Boolean goleada;
    private String tipoClube; // "MANDANTE" ou "VISITANTE"
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
    public LocalDate getData() {

        return data;
    }
    public void setData(LocalDate data) {

        this.data = data;
    }
    public Boolean getGoleada() {

        return goleada;
    }
    public void setGoleada(Boolean goleada) {

        this.goleada = goleada;
    }
    public String getTipoClube() {

        return tipoClube;
    }
    public void setTipoClube(String tipoClube) {

        this.tipoClube = tipoClube;
    }
}











