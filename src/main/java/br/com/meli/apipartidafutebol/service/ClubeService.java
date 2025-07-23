package br.com.meli.apipartidafutebol.service;
import br.com.meli.apipartidafutebol.dto.ClubeRequestDto;
import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;
import br.com.meli.apipartidafutebol.dto.RetrospectoClubeDto;
import br.com.meli.apipartidafutebol.exception.ClubeNaoEncontradoException;
import br.com.meli.apipartidafutebol.exception.ClubesIguaisException;
import br.com.meli.apipartidafutebol.model.Clube;
import br.com.meli.apipartidafutebol.model.Partida;
import br.com.meli.apipartidafutebol.repository.ClubeRepository;
import br.com.meli.apipartidafutebol.repository.PartidaRepository;
import br.com.meli.apipartidafutebol.specification.ClubeSpecification;
import br.com.meli.apipartidafutebol.dto.FiltroClubeRequestDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class ClubeService {

    @Autowired
    private ClubeRepository clubeRepository;

   @Autowired
   private PartidaRepository partidaRepository;


    @Transactional
    public ClubeResponseDto salvar(ClubeRequestDto dto) {
        verificarClubeExistente(dto.getNome(), dto.getSiglaEstado());
        Clube clube = new Clube(
                dto.getNome(),
                dto.getSiglaEstado(),
                dto.getDataCriacao(),
                dto.getAtivo()
        );
        return new ClubeResponseDto(clubeRepository.save(clube));
    }
    public List<ClubeResponseDto> listarTodosClubes() {
        return clubeRepository.findAll()
                .stream()
                .map(ClubeResponseDto::new)
                .toList();
    }
    public ClubeResponseDto buscarPorId(Long id) {
        Clube clube = clubeRepository.findById(id)
                .orElseThrow(() -> new ClubeNaoEncontradoException("Clube não encontrado com ID: " + id));
        return new ClubeResponseDto(clube);
    }
    @Transactional
    public ClubeResponseDto atualizar(Long id, ClubeRequestDto dto) {
        Clube clube = clubeRepository.findById(id)
                .orElseThrow(() -> new ClubeNaoEncontradoException("Clube não encontrado com ID: " + id));
        clube.setNome(dto.getNome());
        clube.setSiglaEstado(dto.getSiglaEstado());
        clube.setDataCriacao(dto.getDataCriacao());
        clube.setAtivo(dto.getAtivo());
        Clube clubeAtualizado = clubeRepository.save(clube);
        return new ClubeResponseDto(clube);
    }
    @Transactional
    public void deletar(Long id) {
        Clube clube = clubeRepository.findById(id)
                .orElseThrow(() -> new ClubeNaoEncontradoException("Clube não encontrado com ID: " + id));
        clubeRepository.delete(clube);
    }
    public void verificarClubeExistente(String nome, String siglaEstado) {
        Optional<Clube> existente = clubeRepository.findByNomeAndSiglaEstado(nome, siglaEstado);
        if (existente.isPresent()) {
            throw new ClubesIguaisException("Já existe um clube com esse nome no mesmo estado.");
        }


    }

    public Page<ClubeResponseDto> filtrarClubes(FiltroClubeRequestDto filtro, Pageable pageable) {
        Specification<Clube> spec = ClubeSpecification.comFiltros(filtro);
        return clubeRepository.findAll(spec, pageable)
                .map(ClubeResponseDto::new);
    }
    public RetrospectoClubeDto obterRetrospecto(Long clubeId) {
        Clube clube = clubeRepository.findById(clubeId)
                .orElseThrow(() -> new ClubeNaoEncontradoException("Clube não encontrado."));
        List<Partida> partidas = partidaRepository.findByClubeMandanteIdOrClubeVisitanteId(clubeId, clubeId);
        int vitorias = 0, empates = 0, derrotas = 0, golsFeitos = 0, golsSofridos = 0;
        for (Partida p : partidas) {
            boolean mandante = p.getClubeMandante().getId().equals(clubeId);
            int golsPro = mandante ? p.getPlacarMandante() : p.getPlacarVisitante();
            int golsContra = mandante ? p.getPlacarVisitante() : p.getPlacarMandante();
            golsFeitos += golsPro;
            golsSofridos += golsContra;
            if (golsPro > golsContra) vitorias++;
            else if (golsPro == golsContra) empates++;
            else derrotas++;
        }
        return new RetrospectoClubeDto(clube.getId(), clube.getNome(), vitorias, empates, derrotas, golsFeitos, golsSofridos);
    }










}

















