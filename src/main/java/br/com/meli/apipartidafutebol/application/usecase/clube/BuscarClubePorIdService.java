package br.com.meli.apipartidafutebol.application.usecase.clube;

import br.com.meli.apipartidafutebol.domain.port.in.BuscarClubePorIdUseCase;
import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;
import br.com.meli.apipartidafutebol.service.ClubeService;
import org.springframework.stereotype.Service;

@Service
public class BuscarClubePorIdService implements BuscarClubePorIdUseCase {
    private final ClubeService svc;
    public BuscarClubePorIdService(ClubeService svc) { this.svc = svc; }
    @Override public ClubeResponseDto executar(Long id) { return svc.buscarPorId(id); }
}
