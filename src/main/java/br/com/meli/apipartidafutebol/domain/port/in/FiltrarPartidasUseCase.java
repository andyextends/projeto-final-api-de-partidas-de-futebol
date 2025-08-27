package br.com.meli.apipartidafutebol.domain.port.in;

import br.com.meli.apipartidafutebol.dto.FiltroPartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FiltrarPartidasUseCase {
    Page<PartidaResponseDto>
executar(FiltroPartidaRequestDto filtro , Pageable pageable);}
