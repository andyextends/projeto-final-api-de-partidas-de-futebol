package br.com.meli.apipartidafutebol.domain.port.in;

import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;

public interface PublicarPartidaUseCase {
    void executar(PartidaRequestDto dto);
}
