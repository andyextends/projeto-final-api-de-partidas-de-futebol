package br.com.meli.apipartidafutebol.adapter.out.persistence;

import br.com.meli.apipartidafutebol.domain.port.out.EstadioRepositoryPort;
import br.com.meli.apipartidafutebol.model.Estadio;
import br.com.meli.apipartidafutebol.repository.EstadioRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
@Component
public class EstadioRepositoryAdapter implements EstadioRepositoryPort {
    private final EstadioRepository springData;
    public EstadioRepositoryAdapter(EstadioRepository springData) {
        this.springData = springData;
    }
    @Override
    public Estadio save(Estadio estadio) {
        return springData.save(estadio);
    }
    @Override
    public Optional<Estadio> findById(Long id) {
        return springData.findById(id);
    }
    @Override
    public List<Estadio> findAll() {
        return springData.findAll();
    }
    @Override
    public boolean existsByNomeAndCidade(String nome, String cidade) {
        return springData.existsByNomeAndCidade(nome,cidade);
    }
}
