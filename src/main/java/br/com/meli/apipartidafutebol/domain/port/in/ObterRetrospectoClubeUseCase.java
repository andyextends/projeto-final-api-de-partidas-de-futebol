package br.com.meli.apipartidafutebol.domain.port.in;

import br.com.meli.apipartidafutebol.dto.RetrospectoClubeDto;
public interface ObterRetrospectoClubeUseCase {
    RetrospectoClubeDto executar(Long id);
}
