package br.com.meli.apipartidafutebol.application.usecase.partida;

import br.com.meli.apipartidafutebol.domain.port.in.ListarPartidasUseCase;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import br.com.meli.apipartidafutebol.service.PartidaService;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ListarPartidasService implements ListarPartidasUseCase {
    private final PartidaService svc;
    public ListarPartidasService(PartidaService svc) { this.svc = svc; }
    @Override public List<PartidaResponseDto> executar() { return svc.listarTodas(); }
}
