package br.com.meli.apipartidafutebol.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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
    // Construtores
    public Partida() {}
    public Partida(Clube clubeMandante, Clube clubeVisitante, Estadio estadio,
                   LocalDateTime dataHora, Integer placarMandante, Integer placarVisitante) {
        this.clubeMandante = clubeMandante;
        this.clubeVisitante = clubeVisitante;
        this.estadio = estadio;
        this.dataHora = dataHora;
        this.placarMandante = placarMandante;
        this.placarVisitante = placarVisitante;
    }
    // Getters e Setters


    public Clube getClubeMandante() {
        return clubeMandante;
    }

    public void setClubeMandante(Clube clubeMandante) {
        this.clubeMandante = clubeMandante;
    }

    public Clube getClubeVisitante() {
        return clubeVisitante;
    }

    public void setClubeVisitante(Clube clubeVisitante) {
        this.clubeVisitante = clubeVisitante;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
