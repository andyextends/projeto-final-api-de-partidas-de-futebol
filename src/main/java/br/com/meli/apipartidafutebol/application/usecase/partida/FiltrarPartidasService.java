package br.com.meli.apipartidafutebol.application.usecase.partida;

import br.com.meli.apipartidafutebol.domain.port.in.FiltrarPartidasUseCase;
import br.com.meli.apipartidafutebol.dto.FiltroPartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import br.com.meli.apipartidafutebol.service.PartidaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class FiltrarPartidasService implements FiltrarPartidasUseCase {
    private final PartidaService svc;
    public FiltrarPartidasService(PartidaService svc) { this.svc = svc; }
    @Override
    public Page<PartidaResponseDto> executar(FiltroPartidaRequestDto filtro, Pageable pageable) {
        return svc.filtrarPartidas(filtro, pageable);
    }
}
