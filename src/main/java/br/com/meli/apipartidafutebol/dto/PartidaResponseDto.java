package br.com.meli.apipartidafutebol.dto;

import br.com.meli.apipartidafutebol.model.Partida;
import java.time.LocalDateTime;
public class PartidaResponseDto {
    private Long id;
    private String clubeMandante;
    private String clubeVisitante;
    private String estadio;
    private LocalDateTime dataHora;
    private Integer placarMandante;
    private Integer placarVisitante;
    public PartidaResponseDto(Partida partida) {
        this.id = partida.getId();
        this.clubeMandante = partida.getClubeMandante().getNome();
        this.clubeVisitante = partida.getClubeVisitante().getNome();
        this.estadio = partida.getEstadio().getNome();
        this.dataHora = partida.getDataHora();
        this.placarMandante = partida.getPlacarMandante();
        this.placarVisitante = partida.getPlacarVisitante();

    }

    public PartidaResponseDto() {

    }

    // Getters
    public Long getId() {
        return id;
    }
    public String getClubeMandante() {
        return clubeMandante;
    }
    public String getClubeVisitante() {
        return clubeVisitante;
    }
    public String getEstadio() {
        return estadio;
    }
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public Integer getPlacarMandante() {
        return placarMandante;
    }
    public Integer getPlacarVisitante() {
        return placarVisitante;
    }
}
