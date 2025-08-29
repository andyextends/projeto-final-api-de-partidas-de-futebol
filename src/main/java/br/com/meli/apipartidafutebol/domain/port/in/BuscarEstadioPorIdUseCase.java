package br.com.meli.apipartidafutebol.domain.port.in;

import br.com.meli.apipartidafutebol.model.Estadio;

public interface BuscarEstadioPorIdUseCase {
    Estadio executar (Long id);
}
