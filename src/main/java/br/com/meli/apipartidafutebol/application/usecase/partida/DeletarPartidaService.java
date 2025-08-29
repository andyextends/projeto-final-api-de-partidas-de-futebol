package br.com.meli.apipartidafutebol.application.usecase.partida;

import br.com.meli.apipartidafutebol.domain.port.in.DeletarPartidaUseCase;
import br.com.meli.apipartidafutebol.service.PartidaService;
import org.springframework.stereotype.Service;
@Service
public class DeletarPartidaService implements DeletarPartidaUseCase {
    private final PartidaService svc;
    public DeletarPartidaService(PartidaService svc) { this.svc = svc; }
    @Override public void executar(Long id) { svc.deletar(id); }
}
