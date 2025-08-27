package br.com.meli.apipartidafutebol.domain.port.in;

import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
public interface CadastrarPartidaUseCase {
    PartidaResponseDto executar(PartidaRequestDto dto);
}
