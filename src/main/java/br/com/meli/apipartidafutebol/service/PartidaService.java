package br.com.meli.apipartidafutebol.service;
import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import br.com.meli.apipartidafutebol.exception.*;
import br.com.meli.apipartidafutebol.model.Clube;
import br.com.meli.apipartidafutebol.model.Estadio;
import br.com.meli.apipartidafutebol.model.Partida;
import br.com.meli.apipartidafutebol.repository.ClubeRepository;
import br.com.meli.apipartidafutebol.repository.EstadioRepository;
import br.com.meli.apipartidafutebol.repository.PartidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.meli.apipartidafutebol.dto.FiltroPartidaRequestDto;
import br.com.meli.apipartidafutebol.specification.PartidaSpecification;

@Service
public class PartidaService {
    @Autowired
    private PartidaRepository partidaRepository;
    @Autowired
    private ClubeRepository clubeRepository;
    @Autowired
    private EstadioRepository estadioRepository;
    @Transactional
    public PartidaResponseDto salvar(PartidaRequestDto dto) {
        Clube mandante = buscarClube(dto.getClubeMandanteId(), "mandante");
        Clube visitante = buscarClube(dto.getClubeVisitanteId(), "visitante");
        Estadio estadio = buscarEstadio(dto.getEstadioId());
        validarRegrasDeNegocio(mandante, visitante, estadio, dto.getDataHora());
        Partida partida = new Partida(
                mandante, visitante, estadio,
                dto.getDataHora(), dto.getPlacarMandante(), dto.getPlacarVisitante()
        );
        return new PartidaResponseDto(partidaRepository.save(partida));
    }
    @Transactional
    public PartidaResponseDto atualizar(Long id, PartidaRequestDto dto) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new PartidaNaoEncontradaException("Partida não encontrada."));
        Clube mandante = buscarClube(dto.getClubeMandanteId(), "mandante");
        Clube visitante = buscarClube(dto.getClubeVisitanteId(), "visitante");
        Estadio estadio = buscarEstadio(dto.getEstadioId());
        validarRegrasDeNegocio(mandante, visitante, estadio, dto.getDataHora());
        partida.setClubeMandante(mandante);
        partida.setClubeVisitante(visitante);
        partida.setEstadio(estadio);
        partida.setDataHora(dto.getDataHora());
        partida.setPlacarMandante(dto.getPlacarMandante());
        partida.setPlacarVisitante(dto.getPlacarVisitante());
        return new PartidaResponseDto(partidaRepository.save(partida));
    }
    public List<PartidaResponseDto> listarTodas() {
        return partidaRepository.findAll()
                .stream()
                .map(PartidaResponseDto::new)
                .collect(Collectors.toList());
    }
    public PartidaResponseDto buscarPorId(Long id) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new PartidaNaoEncontradaException("Partida não encontrada."));
        return new PartidaResponseDto(partida);
    }
    @Transactional
    public void deletar(Long id) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new PartidaNaoEncontradaException("Partida não encontrada."));
        partidaRepository.delete(partida);
    }
    // ===== Métodos auxiliares =====
    private Clube buscarClube(Long id, String tipo) {
        return clubeRepository.findById(id)
                .orElseThrow(() -> new ClubeNaoEncontradoException("Clube " + tipo + " não encontrado."));
    }
    private Estadio buscarEstadio(Long id) {
        return estadioRepository.findById(id)
                .orElseThrow(() -> new EstadioNaoEncontradoException("Estádio não encontrado."));
    }
    private void validarRegrasDeNegocio(Clube mandante, Clube visitante, Estadio estadio, LocalDateTime dataHora) {
        validarClubesDiferentes(mandante, visitante);
        validarClubesAtivos(mandante, visitante);
        validarDataPartidaVsCriacaoClubes(mandante, visitante, dataHora);
        validarEstadioDisponivel(estadio, dataHora);
        validarIntervaloEntrePartidas(mandante, visitante, dataHora);
    }
    private void validarClubesDiferentes(Clube mandante, Clube visitante) {
        if (mandante.getId().equals(visitante.getId())) {
            throw new ClubesIguaisException("Clube mandante e visitante devem ser diferentes.");
        }
    }
    private void validarClubesAtivos(Clube mandante, Clube visitante) {
        if (!mandante.getAtivo() || !visitante.getAtivo()) {
            throw new ClubesInativosException("Ambos os clubes devem estar ativos.");
        }
    }
    private void validarDataPartidaVsCriacaoClubes(Clube mandante, Clube visitante, LocalDateTime dataHora) {
        if (dataHora.isBefore(mandante.getDataCriacao().atStartOfDay()) ||
                dataHora.isBefore(visitante.getDataCriacao().atStartOfDay())) {
            throw new DataInvalidaException("A data da partida não pode ser anterior à criação dos clubes.");
        }
    }
    private void validarEstadioDisponivel(Estadio estadio, LocalDateTime dataHora) {
        boolean ocupado = partidaRepository.findAll().stream()
                .anyMatch(p -> p.getEstadio().getId().equals(estadio.getId())
                        && p.getDataHora().toLocalDate().equals(dataHora.toLocalDate()));
        if (ocupado) {
            throw new EstadioOcupadoException("Já existe uma partida agendada neste estádio neste dia.");
        }
    }
    private void validarIntervaloEntrePartidas(Clube mandante, Clube visitante, LocalDateTime dataHora) {
        boolean intervaloInvalido = partidaRepository.findAll().stream()
                .anyMatch(p -> (
                        p.getClubeMandante().getId().equals(mandante.getId()) ||
                                p.getClubeVisitante().getId().equals(mandante.getId()) ||
                                p.getClubeMandante().getId().equals(visitante.getId()) ||
                                p.getClubeVisitante().getId().equals(visitante.getId())
                ) && Math.abs(p.getDataHora().until(dataHora, ChronoUnit.HOURS)) < 48);
        if (intervaloInvalido) {
            throw new IntervaloInvalidoException("Um dos clubes já possui partida com menos de 48h de intervalo.");
        }
    }

    public Page<PartidaResponseDto> filtrarPartidas(FiltroPartidaRequestDto filtro, Pageable pageable) {
        return partidaRepository.findAll(PartidaSpecification.filtrar(filtro), pageable)
                .map(PartidaResponseDto::new);
    }







}









