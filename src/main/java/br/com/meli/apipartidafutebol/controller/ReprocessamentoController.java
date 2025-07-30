package br.com.meli.apipartidafutebol.controller;

import br.com.meli.apipartidafutebol.integration.PartidaProducer;
import org.springframework.kafka.core.ConsumerFactory;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.web.bind.annotation.*;
import java.time.Duration;
import java.util.Collections;

@RestController
@RequestMapping("/reprocessamento")
public class ReprocessamentoController {
    private final PartidaProducer partidaProducer;
    private final ConsumerFactory<String, String> consumerFactory;
    public ReprocessamentoController(PartidaProducer partidaProducer, ConsumerFactory<String, String> consumerFactory) {
        this.partidaProducer = partidaProducer;
        this.consumerFactory = consumerFactory;
    }
    @PostMapping("/dlt")
    public String reprocessarMensagensDLT() {
        String topicDLT = "cadastro-partida.DLT";
        try (Consumer<String, String> consumer = consumerFactory.createConsumer("grupo-partida-dlt-reprocesso", "")) {
            consumer.subscribe(Collections.singletonList(topicDLT));
            var registros = consumer.poll(Duration.ofSeconds(5));
            if (registros.isEmpty()) {
                return "Nenhuma mensagem na DLT para reprocessar.";
            }
            registros.forEach(record -> {
                String mensagemJson = record.value();
                System.out.println(" repetir Reenviando mensagem da DLT: " + mensagemJson);
                partidaProducer.enviarMensagem(mensagemJson);
            });
            return registros.count() + " mensagens reprocessadas com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao reprocessar mensagens: " + e.getMessage();
        }
    }
}
