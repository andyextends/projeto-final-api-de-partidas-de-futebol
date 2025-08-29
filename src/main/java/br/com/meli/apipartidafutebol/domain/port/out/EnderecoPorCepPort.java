package br.com.meli.apipartidafutebol.domain.port.out;

import br.com.meli.apipartidafutebol.domain.vo.Endereco;

public interface EnderecoPorCepPort {
    Endereco buscar (String cep);
}
