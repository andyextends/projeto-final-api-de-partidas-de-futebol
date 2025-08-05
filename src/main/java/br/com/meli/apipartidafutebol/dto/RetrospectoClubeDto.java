package br.com.meli.apipartidafutebol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrospectoClubeDto {
    private Long clubeId;
    private String nomeClube;
    private int vitorias;
    private int empates;
    private int derrotas;
    private int golsMarcados;
    private int golsSofridos;
    private int totalJogos;

    public RetrospectoClubeDto(Long clubeId, String nomeClube, int vitorias,
                               int empates, int derrotas, int golsMarcados, int golsSofridos) {
        this.clubeId = clubeId;
        this.nomeClube = nomeClube;
        this.vitorias = vitorias;
        this.empates = empates;
        this.derrotas = derrotas;
        this.golsMarcados = golsMarcados;
        this.golsSofridos = golsSofridos;
        this.totalJogos = vitorias + empates + derrotas;
    }

}










