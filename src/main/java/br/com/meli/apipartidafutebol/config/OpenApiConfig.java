package br.com.meli.apipartidafutebol.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;
@OpenAPIDefinition(
        info = @Info(
                title = "API de Partidas de Futebol",
                version = "1.0",
                description = "Sistema para cadastro e gerenciamento de clubes, estádios e partidas de futebol. Inclui regras de negócio específicas e filtros avançados.",
                contact = @Contact(name = "Anderson Freitas", email = "anderson.edsilva@mercadolivre.com"),
                license = @License(name = "Apache 2.0", url = "http://springdoc.org")
        )
)
@Configuration
public class OpenApiConfig {
}











