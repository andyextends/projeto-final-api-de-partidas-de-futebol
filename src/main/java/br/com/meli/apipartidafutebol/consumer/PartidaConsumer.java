package br.com.meli.apipartidafutebol.consumer;

import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import br.com.meli.apipartidafutebol.integration.PartidaProducer;
import br.com.meli.apipartidafutebol.service.PartidaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PartidaConsumer {
    private final PartidaService partidaService;
    private final ObjectMapper objectMapper;
    private final PartidaProducer partidaProducer;
    public PartidaConsumer(PartidaService partidaService,
                           ObjectMapper objectMapper,
                           PartidaProducer partidaProducer) {
        this.partidaService = partidaService;
        this.objectMapper = objectMapper;
        this.partidaProducer = partidaProducer;
    }
    @KafkaListener(
            id = "listener-partida",
            topics = "${spring.kafka.topic.partida}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumir(ConsumerRecord<String, String> record) throws Exception {
        String mensagemJson = record.value();
        System.out.println(" Caixa de entrada - Mensagem recebida: " + mensagemJson);
        Thread.sleep(5000); // opcional: tempo para visualização no Kafdrop
        try {
            PartidaRequestDto dto = objectMapper.readValue(mensagemJson, PartidaRequestDto.class);
            partidaService.salvar(dto);
            System.out.println(" verificação: Partida salva com sucesso!");
        } catch (Exception e) {
            System.err.println(" Erro ao processar partida. Enviando para retry/DLT se necessário: " + e.getMessage());
            throw e; // importante: permite que o errohandler atue
        }
    }
    @KafkaListener(
            topics = "${spring.kafka.topic.partida}.DLT",
            groupId = "grupo-partida-dlt"
    )
    public void reprocessarMensagemDLT(String mensagemJson) {
        System.err.println(" Reprocessando mensagem da DLT: " + mensagemJson);
        try {
            // Validação prévia antes de reenviar
            PartidaRequestDto dto = objectMapper.readValue(mensagemJson, PartidaRequestDto.class);
            partidaService.validarRegrasDeNegocio(dto);

            partidaProducer.enviarMensagem(mensagemJson);
            System.out.println(" verificação: Mensagem reenviada para o tópico principal com sucesso!");
        } catch (Exception e) {
            System.err.println(" Ainda inválida. " +
                    "Não reprocessada faça chamada endpoint reprocessamento/dlt atualizar fila: " + e.getMessage());

            // Aqui você pode persistir a falha ou apenas logar.
        }
    }
}
