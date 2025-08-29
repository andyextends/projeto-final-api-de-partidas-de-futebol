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
        // só dígitos
        String clean = cep == null ? "" : cep.replaceAll("[^0-9]", "");
        EnderecoResponse r = client.buscarEnderecoPorCep(clean);
        if (r == null || r.getLocalidade() == null || r.getLocalidade().isBlank()
                || r.getUf() == null || r.getUf().isBlank()) {
            throw new IllegalArgumentException("CEP inválido ou não encontrado.");
        }
        // usa o CEP de entrada (clean) em vez de r.getCep()
        return new Endereco(
                r.getLogradouro(),
                r.getBairro(),
                r.getLocalidade(),
                r.getUf(),
                clean
        );
    }

}
