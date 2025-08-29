package br.com.meli.apipartidafutebol.application.usecase.clube;

import br.com.meli.apipartidafutebol.domain.port.in.ListarClubesUseCase;
import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;
import br.com.meli.apipartidafutebol.service.ClubeService;
import org.springframework.stereotype.Service;

@Service
public class ListarClubesService implements ListarClubesUseCase {
    private final ClubeService svc;
    public ListarClubesService(ClubeService svc) { this.svc = svc; }
    @Override public java.util.List<ClubeResponseDto> executar() { return svc.listarTodosClubes(); }
}
