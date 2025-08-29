package br.com.meli.apipartidafutebol.application.usecase.clube;

import br.com.meli.apipartidafutebol.domain.port.in.DeletarClubeUseCase;
import br.com.meli.apipartidafutebol.service.ClubeService;
import org.springframework.stereotype.Service;

@Service
public class DeletarClubeService implements DeletarClubeUseCase {
    private final ClubeService svc;
    public DeletarClubeService(ClubeService svc) { this.svc = svc; }
    @Override public void executar(Long id) { svc.deletar(id); }
}
