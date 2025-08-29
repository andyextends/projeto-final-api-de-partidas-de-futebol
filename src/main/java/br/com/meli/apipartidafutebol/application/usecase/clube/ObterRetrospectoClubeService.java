package br.com.meli.apipartidafutebol.application.usecase.clube;

import br.com.meli.apipartidafutebol.domain.port.in.ObterRetrospectoClubeUseCase;
import br.com.meli.apipartidafutebol.dto.RetrospectoClubeDto;
import br.com.meli.apipartidafutebol.service.ClubeService;
import org.springframework.stereotype.Service;

@Service
public class ObterRetrospectoClubeService implements ObterRetrospectoClubeUseCase {
    private final ClubeService svc;
    public ObterRetrospectoClubeService(ClubeService svc) { this.svc = svc; }
    @Override public RetrospectoClubeDto executar(Long id) { return svc.obterRetrospecto(id); }
}
