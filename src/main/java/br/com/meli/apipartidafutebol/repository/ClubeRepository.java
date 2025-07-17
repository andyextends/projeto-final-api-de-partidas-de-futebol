package br.com.meli.apipartidafutebol.repository;

import br.com.meli.apipartidafutebol.model.Clube;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
public interface ClubeRepository extends JpaRepository<Clube, Long>, JpaSpecificationExecutor<Clube> {
    Optional<Clube> findByNomeAndSiglaEstado(String nome, String siglaEstado);
}


