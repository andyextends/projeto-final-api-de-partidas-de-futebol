package br.com.meli.apipartidafutebol.application.usecase.clube;

import br.com.meli.apipartidafutebol.domain.port.in.FiltrarClubesUseCase;
import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;
import br.com.meli.apipartidafutebol.dto.FiltroClubeRequestDto;
import br.com.meli.apipartidafutebol.service.ClubeService;
import org.springframework.stereotype.Service;

@Service
public class FiltrarClubesService implements FiltrarClubesUseCase {
    private final ClubeService svc;
    public FiltrarClubesService(ClubeService svc) { this.svc = svc; }
    @Override public org.springframework.data.domain.Page<ClubeResponseDto> executar(
            FiltroClubeRequestDto filtro, org.springframework.data.domain.Pageable pageable) {
        return svc.filtrarClubes(filtro, pageable);
    }
}
