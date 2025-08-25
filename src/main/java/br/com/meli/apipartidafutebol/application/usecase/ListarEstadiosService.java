package br.com.meli.apipartidafutebol.application.usecase;

import br.com.meli.apipartidafutebol.domain.port.in.ListarEstadiosUseCase;
import br.com.meli.apipartidafutebol.domain.port.out.EstadioRepositoryPort;
import br.com.meli.apipartidafutebol.model.Estadio;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ListarEstadiosService implements ListarEstadiosUseCase {
    private final EstadioRepositoryPort repo;
    public ListarEstadiosService(EstadioRepositoryPort repo) { this.repo = repo; }
    @Override public List<Estadio> executar() { return repo.findAll(); }
}
