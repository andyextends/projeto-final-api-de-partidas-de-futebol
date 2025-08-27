package br.com.meli.apipartidafutebol.application.usecase.clube;

import br.com.meli.apipartidafutebol.domain.port.in.CadastrarClubeUseCase;
import br.com.meli.apipartidafutebol.dto.ClubeRequestDto;
import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;
import br.com.meli.apipartidafutebol.service.ClubeService;
import org.springframework.stereotype.Service;
@Service
public class CadastrarClubeService implements CadastrarClubeUseCase {
    private final ClubeService svc;
    public CadastrarClubeService(ClubeService svc) { this.svc = svc; }
    @Override public ClubeResponseDto executar(ClubeRequestDto dto) {
        return svc.salvar(dto); }
}
