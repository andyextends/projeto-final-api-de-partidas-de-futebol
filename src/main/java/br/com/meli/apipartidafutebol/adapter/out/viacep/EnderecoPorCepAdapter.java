package br.com.meli.apipartidafutebol.adapter.out.viacep;

import br.com.meli.apipartidafutebol.domain.port.out.EnderecoPorCepPort;
import br.com.meli.apipartidafutebol.domain.vo.Endereco;
import br.com.meli.apipartidafutebol.dto.EnderecoResponse;
import br.com.meli.apipartidafutebol.integration.ViaCepClient;
import org.springframework.stereotype.Component;

@Component
public class EnderecoPorCepAdapter implements EnderecoPorCepPort {

    private final ViaCepClient client;

    public EnderecoPorCepAdapter(ViaCepClient client)
    {
        this.client = client;
    }
    @Override
    public Endereco buscar(String cep) {
        EnderecoResponse r = client.buscarEnderecoPorCep(cep);
        String cidade = r.getLocalidade();
        String uf = r.getUf();
        if (cidade == null || uf == null) {
            throw new IllegalArgumentException("CEP inválido ou não encontrado.");
        }
        return new Endereco(r.getLogradouro(), cidade, uf);
    }
}
