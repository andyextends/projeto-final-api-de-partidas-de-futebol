package br.com.meli.apipartidafutebol.specification;

import br.com.meli.apipartidafutebol.model.Clube;
import org.springframework.data.jpa.domain.Specification;
public class ClubeFiltroSpecification {
    public static Specification<Clube> nomeContem(String nome) {
        return (root, query, cb) ->
                nome == null ? null : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }
    public static Specification<Clube> siglaEstadoIgual(String sigla) {
        return (root, query, cb) ->
                sigla == null ? null : cb.equal(cb.lower(root.get("siglaEstado")), sigla.toLowerCase());
    }
    public static Specification<Clube> ativoIgual(Boolean ativo) {
        return (root, query, cb) ->
                ativo == null ? null : cb.equal(root.get("ativo"), ativo);
    }
}







