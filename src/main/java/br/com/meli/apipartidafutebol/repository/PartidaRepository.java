package br.com.meli.apipartidafutebol.repository;

import br.com.meli.apipartidafutebol.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PartidaRepository extends JpaRepository<Partida, Long> {
    // Nenhum método customizado necessário por enquanto
}
