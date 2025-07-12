package br.com.meli.apipartidafutebol.service;

import br.com.meli.apipartidafutebol.dto.EstadioRequestDto;
import br.com.meli.apipartidafutebol.dto.EstadioResponseDto;
import br.com.meli.apipartidafutebol.model.Estadio;
import br.com.meli.apipartidafutebol.repository.EstadioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class EstadioService {
    @Autowired
    private EstadioRepository estadioRepository;
    @Transactional
    public EstadioResponseDto salvar(EstadioRequestDto dto) {
        if (estadioRepository.existsByNomeAndCidade(dto.getNome(), dto.getCidade())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe um estádio com esse nome na mesma cidade."
            );
        }
        Estadio estadio = new Estadio(
                dto.getNome(),
                dto.getCidade(),
                dto.getCapacidade(),
                dto.getAtivo()
        );
        Estadio salvo = estadioRepository.save(estadio);
        return new EstadioResponseDto(salvo);
    }
    public EstadioResponseDto buscarPorId(Long id) {
        Estadio estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Estádio não encontrado com ID: " + id
                ));
        return new EstadioResponseDto(estadio);
    }
    public List<EstadioResponseDto> listarTodos() {
        return estadioRepository.findAll()
                .stream()
                .map(EstadioResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public EstadioResponseDto atualizar(Long id, EstadioRequestDto dto) {
        Estadio estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Estádio não encontrado com ID: " + id
                ));
        estadio.setNome(dto.getNome());
        estadio.setCidade(dto.getCidade());
        estadio.setCapacidade(dto.getCapacidade());
        estadio.setAtivo(dto.getAtivo());
        Estadio atualizado = estadioRepository.save(estadio);
        return new EstadioResponseDto(atualizado);
    }
    @Transactional
    public void deletar(Long id) {
        Estadio estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Estádio não encontrado com ID: " + id
                ));
        estadioRepository.delete(estadio);
    }
}
