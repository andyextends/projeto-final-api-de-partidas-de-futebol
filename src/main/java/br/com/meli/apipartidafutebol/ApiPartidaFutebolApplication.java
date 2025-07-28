package br.com.meli.apipartidafutebol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ApiPartidaFutebolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiPartidaFutebolApplication.class, args);
    }

}
