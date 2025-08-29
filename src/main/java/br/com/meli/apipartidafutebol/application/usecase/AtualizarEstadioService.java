package br.com.meli.apipartidafutebol.application.usecase;

import br.com.meli.apipartidafutebol.domain.port.in.AtualizarEstadioUseCase;
import br.com.meli.apipartidafutebol.domain.port.out.EnderecoPorCepPort;
import br.com.meli.apipartidafutebol.domain.port.out.EstadioRepositoryPort;
import br.com.meli.apipartidafutebol.dto.EstadioRequestDto;
import br.com.meli.apipartidafutebol.model.Estadio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarEstadioService implements AtualizarEstadioUseCase {

    private final EstadioRepositoryPort estadioRepo;
    private final EnderecoPorCepPort enderecoPort;
    public AtualizarEstadioService(EstadioRepositoryPort estadioRepo, EnderecoPorCepPort enderecoPort) {
        this.estadioRepo = estadioRepo;
        this.enderecoPort = enderecoPort;
    }
    @Transactional
    @Override
    public Estadio executar(Long id, EstadioRequestDto dto) {
        Estadio estadio = estadioRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estádio não encontrado"));
        // normalizações básicas
        String nome = normalizar(dto.getNome());
        String cep  = onlyDigits(dto.getCep());
        estadio.setNome(nome);
        estadio.setCapacidade(dto.getCapacidade());
        estadio.setAtivo(dto.getAtivo());
        estadio.setCep(cep);
        // busca endereço e aplica cidade/UF
        var end = enderecoPort.buscar(cep);
        String cidade = normalizar(end.getCidade());
        String uf     = normalizar(end.getUf());
        estadio.setCidade(cidade);
        estadio.setUf(uf);
        // valida duplicidade (nome + cidade)
        if (estadioRepo.existsByNomeAndCidade(estadio.getNome(), estadio.getCidade())) {
            throw new IllegalArgumentException("Já existe estádio com esse nome nessa cidade.");
        }
        return estadioRepo.save(estadio);
    }
    private String normalizar(String s) { return s == null ? null : s.trim().toUpperCase(); }
    private String onlyDigits(String s) { return s == null ? null : s.replaceAll("[^0-9]", ""); }
}
