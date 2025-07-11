package br.com.meli.apipartidafutebol.repository;

import br.com.meli.apipartidafutebol.model.Clube;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ClubeRepository extends JpaRepository<Clube, Long> {
    Optional<Clube> findByNomeAndSiglaEstado(String nome, String siglaEstado);
}


