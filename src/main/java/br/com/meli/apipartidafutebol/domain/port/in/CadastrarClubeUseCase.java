package br.com.meli.apipartidafutebol.domain.port.in;

import br.com.meli.apipartidafutebol.dto.ClubeRequestDto;
import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;

public interface CadastrarClubeUseCase {
   ClubeResponseDto executar(ClubeRequestDto dto);
}
