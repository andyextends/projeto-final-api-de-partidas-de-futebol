package br.com.meli.apipartidafutebol.repository;

import br.com.meli.apipartidafutebol.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartidaRepository extends JpaRepository<Partida, Long>, JpaSpecificationExecutor<Partida> {
    // Nenhum método customizado necessário por enquanto
}
