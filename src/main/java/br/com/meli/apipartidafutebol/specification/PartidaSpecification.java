package br.com.meli.apipartidafutebol.specification;

import br.com.meli.apipartidafutebol.dto.FiltroPartidaRequestDto;
import br.com.meli.apipartidafutebol.model.Partida;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
public class PartidaSpecification {
    public static Specification<Partida> filtrar(FiltroPartidaRequestDto filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filtro.getClubeMandanteId() != null) {
                predicates.add(cb.equal(root.get("clubeMandante").get("id"), filtro.getClubeMandanteId()));
            }
            if (filtro.getClubeVisitanteId() != null) {
                predicates.add(cb.equal(root.get("clubeVisitante").get("id"), filtro.getClubeVisitanteId()));
            }
            if (filtro.getEstadioId() != null) {
                predicates.add(cb.equal(root.get("estadio").get("id"), filtro.getEstadioId()));
            }
            if (filtro.getData() != null) {
                predicates.add(cb.equal(cb.function("date", java.sql.Date.class, root.get("dataHora")), java.sql.Date.valueOf(filtro.getData())));
            }
            if (Boolean.TRUE.equals(filtro.getGoleada())) {
                predicates.add(cb.or(
                        cb.greaterThan(cb.diff(root.get("placarMandante"), root.get("placarVisitante")), 3),
                        cb.greaterThan(cb.diff(root.get("placarVisitante"), root.get("placarMandante")), 3)
                ));
            }
            if ("MANDANTE".equalsIgnoreCase(filtro.getTipoClube())) {
                predicates.add(cb.equal(root.get("clubeMandante").get("id"), filtro.getClubeMandanteId()));
            } else if ("VISITANTE".equalsIgnoreCase(filtro.getTipoClube())) {
                predicates.add(cb.equal(root.get("clubeVisitante").get("id"), filtro.getClubeVisitanteId()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

