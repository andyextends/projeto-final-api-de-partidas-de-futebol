package br.com.meli.apipartidafutebol.domain.port.in;

import br.com.meli.apipartidafutebol.model.Estadio;

public interface CadastrarEstadioUseCase {
    Estadio executar (Estadio estadio);
}
