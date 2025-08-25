package br.com.meli.apipartidafutebol.domain.port.in;

import br.com.meli.apipartidafutebol.model.Estadio;

import java.util.List;

public interface ListarEstadiosUseCase {
    List<Estadio> executar ();
}
