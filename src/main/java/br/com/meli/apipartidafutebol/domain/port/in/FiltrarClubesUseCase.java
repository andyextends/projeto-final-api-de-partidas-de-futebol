package br.com.meli.apipartidafutebol.domain.port.in;

import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;
import br.com.meli.apipartidafutebol.dto.FiltroClubeRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface FiltrarClubesUseCase {
    Page<ClubeResponseDto> executar(FiltroClubeRequestDto filtro, Pageable pageable);
}
