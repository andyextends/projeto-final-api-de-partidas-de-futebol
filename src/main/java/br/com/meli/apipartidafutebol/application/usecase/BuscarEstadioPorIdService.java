package br.com.meli.apipartidafutebol.application.usecase;

import br.com.meli.apipartidafutebol.domain.port.in.BuscarEstadioPorIdUseCase;
import br.com.meli.apipartidafutebol.domain.port.out.EstadioRepositoryPort;
import br.com.meli.apipartidafutebol.model.Estadio;
import org.springframework.stereotype.Service;
@Service
public class BuscarEstadioPorIdService implements BuscarEstadioPorIdUseCase {
    private final EstadioRepositoryPort repo;
    public BuscarEstadioPorIdService(EstadioRepositoryPort repo) { this.repo = repo; }
    @Override public Estadio executar(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Estádio não encontrado"));
    }
}

