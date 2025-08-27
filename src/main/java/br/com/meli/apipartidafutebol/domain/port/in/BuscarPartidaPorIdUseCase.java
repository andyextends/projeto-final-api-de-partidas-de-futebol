package br.com.meli.apipartidafutebol.domain.port.in;

import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
public interface BuscarPartidaPorIdUseCase {
    PartidaResponseDto executar(Long id);
}
