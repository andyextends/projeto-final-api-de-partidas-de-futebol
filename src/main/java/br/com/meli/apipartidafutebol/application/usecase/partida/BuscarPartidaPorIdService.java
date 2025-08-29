package br.com.meli.apipartidafutebol.application.usecase.partida;

import br.com.meli.apipartidafutebol.domain.port.in.BuscarPartidaPorIdUseCase;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import br.com.meli.apipartidafutebol.service.PartidaService;
import org.springframework.stereotype.Service;
@Service
public class BuscarPartidaPorIdService implements BuscarPartidaPorIdUseCase {
    private final PartidaService svc;
    public BuscarPartidaPorIdService(PartidaService svc) { this.svc = svc; }
    @Override public PartidaResponseDto executar(Long id) { return svc.buscarPorId(id); }
}
