package br.com.meli.apipartidafutebol.domain.port.in;

import java.util.List;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
public interface ListarPartidasUseCase {
    List<PartidaResponseDto> executar();
}
