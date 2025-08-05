package br.com.meli.apipartidafutebol.controller;
import br.com.meli.apipartidafutebol.integration.PartidaProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.web.bind.annotation.*;
import java.time.Duration;
import java.util.Collections;

@RestController
@RequestMapping("/reprocessamento")
public class ReprocessamentoController {
    private final PartidaProducer partidaProducer;
    private final ConsumerFactory<String, String> consumerFactory;
    public ReprocessamentoController(PartidaProducer partidaProducer,
                                     ConsumerFactory<String, String> consumerFactory) {
        this.partidaProducer = partidaProducer;
        this.consumerFactory = consumerFactory;
    }
    @PostMapping("/dlt")
    @Operation(
            summary = "Reprocessar mensagens da DLT",
            description = "Reenvia manualmente mensagens do tópico cadastro-partida.DLT para o tópico principal do Kafka"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensagens reprocessadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma mensagem encontrada na DLT"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao tentar reprocessar mensagens")
    })
    public ResponseEntity<String> reprocessarMensagensDLT() {
        String topicDLT = "cadastro-partida.DLT";
        try (Consumer<String, String> consumer = consumerFactory.createConsumer("grupo-partida-dlt-reprocesso", "")) {
            consumer.subscribe(Collections.singletonList(topicDLT));
            ConsumerRecords<String, String> registros = consumer.poll(Duration.ofSeconds(5));
            if (registros.isEmpty()) {
                return ResponseEntity.status(404).body("Nenhuma mensagem na DLT para reprocessar.");
            }
            int total = 0;
            for (ConsumerRecord<String, String> record : registros) {
                String mensagemJson = record.value();
                System.out.println(" Reenviando mensagem da DLT: " + mensagemJson);
                partidaProducer.enviarMensagem(mensagemJson);
                total++;
            }
            return ResponseEntity.ok(total + " mensagem(ns) reprocessada(s) com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Erro ao reprocessar mensagens: " + e.getMessage());
        }
    }
}
