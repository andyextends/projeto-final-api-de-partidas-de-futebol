package br.com.meli.apipartidafutebol.service;
import br.com.meli.apipartidafutebol.dto.ClubeRequestDto;
import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;
import br.com.meli.apipartidafutebol.exception.ClubeNaoEncontradoException;
import br.com.meli.apipartidafutebol.exception.ClubesIguaisException;
import br.com.meli.apipartidafutebol.model.Clube;
import br.com.meli.apipartidafutebol.repository.ClubeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ClubeService {
    @Autowired
    private ClubeRepository clubeRepository;
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
        return new ClubeResponseDto(clubeRepository.save(clube));
    }
    @Transactional
    public void deletar(Long id) {
        Clube clube = clubeRepository.findById(id)
                .orElseThrow(() -> new ClubeNaoEncontradoException("Clube não encontrado com ID: " + id));
        clubeRepository.delete(clube);
    }
    private void verificarClubeExistente(String nome, String siglaEstado) {
        boolean existe = clubeRepository.findByNomeAndSiglaEstado(nome, siglaEstado).isPresent();
        if (existe) {
            throw new ClubesIguaisException("Já existe um clube com esse nome no mesmo estado.");
        }
    }
}















