package br.com.meli.apipartidafutebol.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "partidas")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "clube_mandante_id", nullable = false)
    private Clube clubeMandante;
    @ManyToOne(optional = false)
    @JoinColumn(name = "clube_visitante_id", nullable = false)
    private Clube clubeVisitante;
    @ManyToOne(optional = false)
    @JoinColumn(name = "estadio_id", nullable = false)
    private Estadio estadio;
    @Column(nullable = false)
    private LocalDateTime dataHora;
    private Integer placarMandante;
    private Integer placarVisitante;

    public Partida(Clube clubeMandante, Clube clubeVisitante, Estadio estadio,
                   LocalDateTime dataHora, Integer placarMandante, Integer placarVisitante) {
        this.clubeMandante = clubeMandante;
        this.clubeVisitante = clubeVisitante;
        this.estadio = estadio;
        this.dataHora = dataHora;
        this.placarMandante = placarMandante;
        this.placarVisitante = placarVisitante;
    }

}
