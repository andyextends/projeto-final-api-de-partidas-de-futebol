package br.com.meli.apipartidafutebol.domain.port.in;

import br.com.meli.apipartidafutebol.model.Estadio;

public interface AtualizarEstadioUseCase {
    Estadio executar (Long id, Estadio estadio);
}
