package br.com.meli.apipartidafutebol.config;

import br.com.meli.apipartidafutebol.integration.PartidaProducer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.Collections;
@Component
public class ScheduledReprocessadorDLT {
    private final ConsumerFactory<String, String> consumerFactory;
    private final PartidaProducer partidaProducer;
    public ScheduledReprocessadorDLT(ConsumerFactory<String, String> consumerFactory,
                                     PartidaProducer partidaProducer) {
        this.consumerFactory = consumerFactory;
        this.partidaProducer = partidaProducer;
    }
    @Scheduled(cron = "0 */5 * * * *", zone = "America/Sao_Paulo") // A cada 5 min
    public void reprocessarAutomaticamente() {
        String dltTopic = "cadastro-partida.DLT";
        System.out.println(" Verificando mensagens na DLT para reprocessar...");
        try (Consumer<String, String> consumer = consumerFactory.createConsumer("grupo-dlt-scheduler", "")) {
            consumer.subscribe(Collections.singletonList(dltTopic));
            var registros = consumer.poll(Duration.ofSeconds(5));
            if (registros.isEmpty()) {
                System.out.println(" Nenhuma mensagem DLT para reprocessar.");
                return;
            }
            for (ConsumerRecord<String, String> record : registros) {
                String mensagem = record.value();
                System.out.println(" Reenviando DLT para tópico principal: " + mensagem);
                partidaProducer.enviarMensagem(mensagem);
            }
            System.out.println(" Reprocessamento automático concluído: " + registros.count() + " mensagens.");
        } catch (Exception e) {
            System.err.println(" Erro ao reprocessar DLT: " + e.getMessage());
        }
    }
}
