package br.com.meli.apipartidafutebol.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;



@Service
public class PartidaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${spring.kafka.topic.partida}")
    private String topicName;
    public PartidaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void enviarMensagem(String mensagemJson) {
        String mensagemId= UUID.randomUUID().toString();
        System.out.println(" Producer Enviando mensagem ID para o tópico: "+ mensagemId + " -> " + mensagemJson);
        CompletableFuture<?> future = kafkaTemplate.send(topicName, mensagemJson);
        future.whenComplete( (       result ,ex) -> {
            if (ex != null) {
                System.out.println(" Producer Mensagem ID " + mensagemId + "Mensagem enviada com sucesso Kafka: " );
            } else {
                System.out.println(" Producer Falha ao enviar Mensagem ID " + mensagemId + ": " + ex.getMessage());
            }



    });

        }
    }