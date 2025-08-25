package br.com.meli.apipartidafutebol.application.usecase;

import br.com.meli.apipartidafutebol.domain.port.in.AtualizarEstadioUseCase;
import br.com.meli.apipartidafutebol.domain.port.out.EnderecoPorCepPort;
import br.com.meli.apipartidafutebol.domain.port.out.EstadioRepositoryPort;
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
    public Estadio executar(Long id, Estadio dto) {
        var estadio = estadioRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estádio não encontrado"));
        estadio.setNome(dto.getNome());
        estadio.setCapacidade(dto.getCapacidade());
        estadio.setAtivo(dto.getAtivo());
        estadio.setCep(dto.getCep());
        var end = enderecoPort.buscar(estadio.getCep());
        if (end != null) {
            estadio.setCidade(end.cidade());
        }
        return estadioRepo.save(estadio);
    }
}
