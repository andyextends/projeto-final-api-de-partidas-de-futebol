package br.com.meli.apipartidafutebol.service;

import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import br.com.meli.apipartidafutebol.model.Clube;
import br.com.meli.apipartidafutebol.model.Estadio;
import br.com.meli.apipartidafutebol.model.Partida;
import br.com.meli.apipartidafutebol.repository.ClubeRepository;
import br.com.meli.apipartidafutebol.repository.EstadioRepository;
import br.com.meli.apipartidafutebol.repository.PartidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

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
        Clube mandante = clubeRepository.findById(dto.getClubeMandanteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clube mandante não encontrado."));
        Clube visitante = clubeRepository.findById(dto.getClubeVisitanteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clube visitante não encontrado."));
        if (mandante.getId().equals(visitante.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clube mandante e visitante devem ser diferentes.");
        }
        if (!mandante.getAtivo() || !visitante.getAtivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ambos os clubes devem estar ativos.");
        }
        if (dto.getDataHora().isBefore(mandante.getDataCriacao().atStartOfDay()) ||
                dto.getDataHora().isBefore(visitante.getDataCriacao().atStartOfDay())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data da partida não pode ser anterior à criação dos clubes.");
        }
        Estadio estadio = estadioRepository.findById(dto.getEstadioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estádio não encontrado."));
        // Verifica se já há partida no mesmo estádio e dia
        boolean estadioOcupado = partidaRepository.findAll().stream()
                .anyMatch(p -> p.getEstadio().getId().equals(estadio.getId())
                        && p.getDataHora().toLocalDate().equals(dto.getDataHora().toLocalDate()));
        if (estadioOcupado) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma partida agendada neste estádio neste dia.");
        }
        // Verifica intervalo mínimo de 48h para o mandante
        boolean intervaloInvalidoMandante = partidaRepository.findAll().stream()
                .anyMatch(p -> (p.getClubeMandante().getId().equals(mandante.getId())
                        || p.getClubeVisitante().getId().equals(mandante.getId()))
                        && Math.abs(p.getDataHora().until(dto.getDataHora(), java.time.temporal.ChronoUnit.HOURS)) < 48);
        boolean intervaloInvalidoVisitante = partidaRepository.findAll().stream()
                .anyMatch(p -> (p.getClubeMandante().getId().equals(visitante.getId())
                        || p.getClubeVisitante().getId().equals(visitante.getId()))
                        && Math.abs(p.getDataHora().until(dto.getDataHora(), java.time.temporal.ChronoUnit.HOURS)) < 48);
        if (intervaloInvalidoMandante || intervaloInvalidoVisitante) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Um dos clubes já possui partida marcada com menos de 48h de intervalo.");
        }
        Partida partida = new Partida(
                mandante,
                visitante,
                estadio,
                dto.getDataHora(),
                dto.getPlacarMandante(),
                dto.getPlacarVisitante()
        );
        Partida salva = partidaRepository.save(partida);
        return new PartidaResponseDto(salva);
    }
    public List<PartidaResponseDto> listarTodas() {
        return partidaRepository.findAll()
                .stream()
                .map(PartidaResponseDto::new)
                .collect(Collectors.toList());
    }
    public PartidaResponseDto buscarPorId(Long id) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada."));
        return new PartidaResponseDto(partida);
    }
    @Transactional
    public PartidaResponseDto atualizar(Long id, PartidaRequestDto dto) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada."));
        Clube mandante = clubeRepository.findById(dto.getClubeMandanteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clube mandante não encontrado."));
        Clube visitante = clubeRepository.findById(dto.getClubeVisitanteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clube visitante não encontrado."));
        if (mandante.getId().equals(visitante.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clube mandante e visitante devem ser diferentes.");
        }
        Estadio estadio = estadioRepository.findById(dto.getEstadioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estádio não encontrado."));
        partida.setClubeMandante(mandante);
        partida.setClubeVisitante(visitante);
        partida.setEstadio(estadio);
        partida.setDataHora(dto.getDataHora());
        partida.setPlacarMandante(dto.getPlacarMandante());
        partida.setPlacarVisitante(dto.getPlacarVisitante());
        Partida atualizada = partidaRepository.save(partida);
        return new PartidaResponseDto(atualizada);
    }
    @Transactional
    public void deletar(Long id) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada."));
        partidaRepository.delete(partida);
    }
}
