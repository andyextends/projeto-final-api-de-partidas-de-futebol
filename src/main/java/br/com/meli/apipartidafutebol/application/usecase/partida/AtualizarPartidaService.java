package br.com.meli.apipartidafutebol.application.usecase.partida;

import br.com.meli.apipartidafutebol.domain.port.in.AtualizarPartidaUseCase;
import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import br.com.meli.apipartidafutebol.service.PartidaService;
import org.springframework.stereotype.Service;
@Service
public class AtualizarPartidaService implements AtualizarPartidaUseCase {
    private final PartidaService svc;
    public AtualizarPartidaService(PartidaService svc) { this.svc = svc; }
    @Override public PartidaResponseDto executar(Long id, PartidaRequestDto dto) { return svc.atualizar(id, dto); }
}
