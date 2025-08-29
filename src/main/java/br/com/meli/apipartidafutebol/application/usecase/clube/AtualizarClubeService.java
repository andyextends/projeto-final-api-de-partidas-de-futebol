package br.com.meli.apipartidafutebol.application.usecase.clube;

import br.com.meli.apipartidafutebol.domain.port.in.AtualizarClubeUseCase;
import br.com.meli.apipartidafutebol.dto.ClubeRequestDto;
import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;
import br.com.meli.apipartidafutebol.service.ClubeService;
import org.springframework.stereotype.Service;

@Service
public class AtualizarClubeService implements AtualizarClubeUseCase {
    private final ClubeService svc;
    public AtualizarClubeService(ClubeService svc) { this.svc = svc; }
    @Override public ClubeResponseDto executar(Long id, ClubeRequestDto dto) { return svc.atualizar(id, dto); }
}
