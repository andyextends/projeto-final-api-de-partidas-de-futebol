package br.com.meli.apipartidafutebol.service;

import br.com.meli.apipartidafutebol.dto.ClubeRequestDto;
import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;
import br.com.meli.apipartidafutebol.model.Clube;
import br.com.meli.apipartidafutebol.repository.ClubeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubeService {
    @Autowired
    private ClubeRepository clubeRepository;
    @Transactional
    public ClubeResponseDto salvar(ClubeRequestDto dto ){
        // Verifica se já existe clube com mesmo nome e siglaEstado
        boolean existe = clubeRepository
                .findByNomeAndSiglaEstado(dto.getNome(), dto.getSiglaEstado())
                .isPresent();
        if (existe) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Já existe um clube com esse nome no mesmo estado.");
        }
        // Cria e salva entidade
        Clube clube = new Clube(
                dto.getNome(),
                dto.getSiglaEstado(),
                dto.getDataCriacao(),
                dto.getAtivo()
        );

        Clube clubeSalvo = clubeRepository.save(clube);
        return new
        ClubeResponseDto(clubeSalvo);

    }
    public List<ClubeResponseDto> listarTodosClubes(){
        return clubeRepository.findAll().stream().map(ClubeResponseDto::new).collect(Collectors.toList());

    }

    public ClubeResponseDto buscarPorId(Long id) {
        Clube clube = clubeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Clube não encontrado com o ID: " + id));
return new ClubeResponseDto(clube);
    }
    public ClubeResponseDto atualizar(Long id, ClubeRequestDto dto) {
        Clube clube = clubeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Clube não encontrado com ID: " + id
                ));
        // Atualiza os campos
        clube.setNome(dto.getNome());
        clube.setSiglaEstado(dto.getSiglaEstado());
        clube.setDataCriacao(dto.getDataCriacao());
        clube.setAtivo(dto.getAtivo());
        Clube atualizado = clubeRepository.save(clube);
        return new ClubeResponseDto(atualizado);
    }
    public void deletar(Long id) {
        Clube clube = clubeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Clube não encontrado com ID: " + id
                ));
        clubeRepository.delete(clube);
    }
}








