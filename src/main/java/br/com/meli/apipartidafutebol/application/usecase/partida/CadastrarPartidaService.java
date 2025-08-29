package br.com.meli.apipartidafutebol.application.usecase.partida;

import br.com.meli.apipartidafutebol.domain.port.in.CadastrarPartidaUseCase;
import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import br.com.meli.apipartidafutebol.service.PartidaService;
import org.springframework.stereotype.Service;
@Service
public class CadastrarPartidaService implements CadastrarPartidaUseCase {
    private final PartidaService svc;
    public CadastrarPartidaService(PartidaService svc) { this.svc = svc; }
    @Override public PartidaResponseDto executar(PartidaRequestDto dto) { return svc.salvar(dto); }
}
