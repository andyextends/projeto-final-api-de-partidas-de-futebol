package br.com.meli.apipartidafutebol.repository;

import br.com.meli.apipartidafutebol.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long>, JpaSpecificationExecutor<Partida> {
    List<Partida> findByClubeMandanteIdOrClubeVisitanteId(Long mandanteId, Long visitanteId);

}
