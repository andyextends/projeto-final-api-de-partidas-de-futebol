package br.com.meli.apipartidafutebol.repository;

import br.com.meli.apipartidafutebol.model.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadioRepository extends JpaRepository<Estadio, Long> {
    // Exemplo de método opcional, se quiser evitar duplicidade de nome + cidade
    boolean existsByNomeAndCidade(String nome, String cidade);
}
