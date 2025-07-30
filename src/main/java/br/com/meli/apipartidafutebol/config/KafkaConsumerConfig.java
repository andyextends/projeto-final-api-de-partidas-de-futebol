package br.com.meli.apipartidafutebol.config;
import br.com.meli.apipartidafutebol.exception.DataInvalidaException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;

@Configuration
public class KafkaConsumerConfig {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            ConsumerFactory<String, String> consumerFactory,
            DefaultErrorHandler errorHandler
    ) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, String> kafkaTemplate) {
        // Redireciona para a DLT após falha
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);
        // Retry com backoff: 3 tentativas, começando com 3s e dobrando
        ExponentialBackOffWithMaxRetries backOff = new ExponentialBackOffWithMaxRetries(3);
        backOff.setInitialInterval(3000);
        backOff.setMultiplier(2.0);
        backOff.setMaxInterval(20000);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);
        // Apenas erros de negócio vão direto para a DLT
        errorHandler.addNotRetryableExceptions(DataInvalidaException.class);
        // Log para cada tentativa
        errorHandler.setRetryListeners((record, ex, attempt) -> {
            System.err.println("repetir Tentativa " + attempt + " falhou: " + record.value());
        });
        return errorHandler;
    }
}
