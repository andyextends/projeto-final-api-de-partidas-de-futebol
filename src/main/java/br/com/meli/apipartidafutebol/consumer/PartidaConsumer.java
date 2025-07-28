package br.com.meli.apipartidafutebol.consumer;

import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import br.com.meli.apipartidafutebol.service.PartidaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PartidaConsumer {
    private final PartidaService partidaService;
    private final ObjectMapper objectMapper;
    public PartidaConsumer(PartidaService partidaService, ObjectMapper objectMapper) {
        this.partidaService = partidaService;
        this.objectMapper = objectMapper;
    }
    @KafkaListener(
            id = "listener-partida",
            topics = "${spring.kafka.topic.partida}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumir(ConsumerRecord<String, String> record) {
        try {
            // Espera de 5 segundos para visualização da mensagem no Kafdrop
            System.out.println(" Aguardando antes de processar mensagem...");
            Thread.sleep(5000);
            String mensagemJson = record.value();
            System.out.println(" Mensagem recebida do Kafka: " + mensagemJson);
            PartidaRequestDto dto = objectMapper.readValue(mensagemJson, PartidaRequestDto.class);
            partidaService.salvar(dto);
        } catch (Exception e) {
            System.err.println(" Erro ao processar mensagem do Kafka: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
