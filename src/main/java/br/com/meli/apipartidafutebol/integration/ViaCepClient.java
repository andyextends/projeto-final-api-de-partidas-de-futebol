package br.com.meli.apipartidafutebol.integration;
import br.com.meli.apipartidafutebol.dto.EnderecoResponse;
import br.com.meli.apipartidafutebol.exception.CepNaoEncontradoException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
@Component
public class ViaCepClient {
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";
    private final RestTemplate restTemplate = new RestTemplate();
    public EnderecoResponse buscarEnderecoPorCep(String cep) {
        cep = cep.replaceAll("[^0-9]", ""); // limpa o CEP
        String url = UriComponentsBuilder.fromUriString(VIA_CEP_URL)
                .buildAndExpand(cep)
                .toUriString();
        EnderecoResponse endereco = restTemplate.getForObject(url, EnderecoResponse.class);
        if (endereco == null || endereco.getLocalidade() == null || endereco.getUf() == null) {
            {
                throw new CepNaoEncontradoException("CEP inválido ou não encontrado.");
            }
        }
        return endereco;
    }
}
