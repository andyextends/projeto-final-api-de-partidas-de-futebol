package br.com.meli.apipartidafutebol.domain.port.in;

import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;
import br.com.meli.apipartidafutebol.model.Clube;

import java.util.List;

public interface ListarClubesUseCase {
    List<ClubeResponseDto> executar();
}
