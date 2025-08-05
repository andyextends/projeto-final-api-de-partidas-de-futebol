package br.com.meli.apipartidafutebol.dto;

import br.com.meli.apipartidafutebol.model.Partida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
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


}
