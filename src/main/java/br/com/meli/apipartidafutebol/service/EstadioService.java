package br.com.meli.apipartidafutebol.service;
import br.com.meli.apipartidafutebol.dto.EnderecoResponse;
import br.com.meli.apipartidafutebol.dto.EstadioRequestDto;
import br.com.meli.apipartidafutebol.dto.EstadioResponseDto;
import br.com.meli.apipartidafutebol.exception.CepNaoEncontradoException;
import br.com.meli.apipartidafutebol.exception.EstadioIndisponivelException;
import br.com.meli.apipartidafutebol.exception.EstadioNaoEncontradoException;
import br.com.meli.apipartidafutebol.integration.ViaCepClient;
import br.com.meli.apipartidafutebol.model.Estadio;
import br.com.meli.apipartidafutebol.repository.EstadioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class EstadioService {
    @Autowired
    private EstadioRepository estadioRepository;
    @Autowired
    private ViaCepClient viaCepClient;
    @Transactional
    public EstadioResponseDto salvar(EstadioRequestDto dto) {
        String cepFormatado = dto.getCep().replaceAll("\\D", "");
        if (!cepFormatado.matches("\\d{8}")) {
            throw new CepNaoEncontradoException("O CEP deve conter exatamente 8 dígitos numéricos.");
        }
        dto.setCep(cepFormatado); // Atualiza o DTO com o CEP limpo
        EnderecoResponse endereco = viaCepClient.buscarEnderecoPorCep(dto.getCep());
        if (endereco == null || endereco.getLocalidade() == null) {
            throw new IllegalArgumentException("CEP inválido ou não encontrado: " + dto.getCep());
        }
        dto.setCidade(endereco.getLocalidade()); // Atualiza cidade com base no ViaCEP
        verificarEstadioDuplicado(dto.getNome(), dto.getCidade());
        Estadio estadio = new Estadio(
                dto.getNome(),
                dto.getCidade(),
                dto.getCapacidade(),
                dto.getAtivo(),
                dto.getCep()
        );
        return new EstadioResponseDto(estadioRepository.save(estadio));
    }
    public EstadioResponseDto buscarPorId(Long id) {
        Estadio estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new EstadioNaoEncontradoException("Estádio não encontrado com ID: " + id));
        return new EstadioResponseDto(estadio);
    }
    public List<EstadioResponseDto> listarTodos() {
        return estadioRepository.findAll()
                .stream()
                .map(EstadioResponseDto::new)
                .toList();
    }
    @Transactional
    public EstadioResponseDto atualizar(Long id, EstadioRequestDto dto) {
        Estadio estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new EstadioNaoEncontradoException("Estádio não encontrado com ID: " + id));
        estadio.setNome(dto.getNome());
        estadio.setCidade(dto.getCidade());
        estadio.setCapacidade(dto.getCapacidade());
        estadio.setAtivo(dto.getAtivo());
        estadio.setCep(dto.getCep());
        return new EstadioResponseDto(estadioRepository.save(estadio));
    }
    @Transactional
    public void deletar(Long id) {
        Estadio estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new EstadioNaoEncontradoException("Estádio não encontrado com ID: " + id));
        estadioRepository.delete(estadio);
    }
    private void verificarEstadioDuplicado(String nome, String cidade) {
        if (estadioRepository.existsByNomeAndCidade(nome, cidade)) {
            throw new EstadioIndisponivelException("Já existe um estádio com esse nome na mesma cidade.");
        }
    }
}






