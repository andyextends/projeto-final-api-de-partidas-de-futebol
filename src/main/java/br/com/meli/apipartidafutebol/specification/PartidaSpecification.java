package br.com.meli.apipartidafutebol.specification;

import br.com.meli.apipartidafutebol.dto.FiltroPartidaRequestDto;
import br.com.meli.apipartidafutebol.model.Partida;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class PartidaSpecification {

    public static Specification<Partida> filtrar(FiltroPartidaRequestDto filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            // TipoClube vem antes: prioriza mandante ou visitante
            if ("MANDANTE".equalsIgnoreCase(filtro.getTipoClube()) && filtro.getClubeMandanteId() != null) {
                predicates.add(cb.equal(root.get("clubeMandante").get("id"), filtro.getClubeMandanteId()));
            } else if ("VISITANTE".equalsIgnoreCase(filtro.getTipoClube()) && filtro.getClubeVisitanteId() != null) {
                predicates.add(cb.equal(root.get("clubeVisitante").get("id"), filtro.getClubeVisitanteId()));
            } else {
                // Caso tipoClube não seja usado, aplica os dois se existirem
                if (filtro.getClubeMandanteId() != null) {
                    predicates.add(cb.equal(root.get("clubeMandante").get("id"), filtro.getClubeMandanteId()));
                }
                if (filtro.getClubeVisitanteId() != null) {
                    predicates.add(cb.equal(root.get("clubeVisitante").get("id"), filtro.getClubeVisitanteId()));
                }
            }
            // Estádio
            if (filtro.getEstadioId() != null) {
                predicates.add(cb.equal(root.get("estadio").get("id"), filtro.getEstadioId()));
            }
            // Data
            if (filtro.getData() != null) {
                predicates.add(cb.equal(
                        cb.function("date", java.sql.Date.class, root.get("dataHora")),
                        java.sql.Date.valueOf(filtro.getData())
                ));
            }
            // Goleada
            if (Boolean.TRUE.equals(filtro.getGoleada())) {
                Expression<Integer> diferenca = cb.diff(root.get("placarMandante"), root.get("placarVisitante"));
                Expression<Integer> absDiferenca = cb.function("abs", Integer.class, diferenca);
                predicates.add(cb.greaterThanOrEqualTo(absDiferenca, 3));
            } else if (Boolean.FALSE.equals(filtro.getGoleada())) {
                Expression<Integer> diferenca = cb.diff(root.get("placarMandante"), root.get("placarVisitante"));
                Expression<Integer> absDiferenca = cb.function("abs", Integer.class, diferenca);
                predicates.add(cb.lessThan(absDiferenca, 3));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}