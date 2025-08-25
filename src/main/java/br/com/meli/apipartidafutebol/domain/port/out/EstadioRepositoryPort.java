package br.com.meli.apipartidafutebol.domain.port.out;

import br.com.meli.apipartidafutebol.model.Estadio;

import java.util.List;
import java.util.Optional;

public interface EstadioRepositoryPort {
    Estadio save(Estadio estadio);
    Optional<Estadio> findById(Long id);
    List<Estadio> findAll();
    boolean existsByNome(String nome);
}
