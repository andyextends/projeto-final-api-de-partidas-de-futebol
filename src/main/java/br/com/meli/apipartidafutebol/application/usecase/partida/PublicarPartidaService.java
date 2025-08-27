package br.com.meli.apipartidafutebol.application.usecase.partida;

import br.com.meli.apipartidafutebol.domain.port.in.PublicarPartidaUseCase;
import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import br.com.meli.apipartidafutebol.integration.PartidaProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;
@Service
public class PublicarPartidaService implements PublicarPartidaUseCase {
    private final PartidaProducer producer;
    private final ObjectMapper mapper;
    public PublicarPartidaService(PartidaProducer producer) {
        this.producer = producer;
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    @Override
    public void executar(PartidaRequestDto dto) {
        try {
            String json = mapper.writeValueAsString(dto);
            producer.enviarMensagem(json);
        } catch (Exception e) {
            throw new IllegalArgumentException("Falha ao publicar partida", e);
        }
    }
}
