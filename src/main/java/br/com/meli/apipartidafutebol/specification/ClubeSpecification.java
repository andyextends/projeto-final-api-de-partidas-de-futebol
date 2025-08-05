package br.com.meli.apipartidafutebol.specification;
import br.com.meli.apipartidafutebol.dto.FiltroClubeRequestDto;
import br.com.meli.apipartidafutebol.model.Clube;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ClubeSpecification {

    public static Specification<Clube> comFiltros(FiltroClubeRequestDto filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nome")),
                        "%" + filtro.getNome().toLowerCase() + "%"
                ));
            }
            if (filtro.getSiglaEstado() != null && !filtro.getSiglaEstado().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        root.get("siglaEstado"), filtro.getSiglaEstado()
                ));
            }
            if (filtro.getAtivo() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("ativo"), filtro.getAtivo()
                ));
            }
            if (filtro.getDataCriacaoInicial() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("dataCriacao"), filtro.getDataCriacaoInicial()
                ));
            }
            if (filtro.getDataCriacaoFinal() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("dataCriacao"), filtro.getDataCriacaoFinal()
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}