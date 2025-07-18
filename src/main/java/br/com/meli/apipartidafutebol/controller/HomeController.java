package br.com.meli.apipartidafutebol.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Home", description = "Página inicial da API")
@RestController
@RequestMapping("/")
public class HomeController {
    @Operation(summary = "Exibe mensagem inicial da API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mensagem de boas-vindas retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<String> index() {
        return ResponseEntity.ok(
                "Bem-vindo à API de Partidas de Futebol!\n" +
                        "Acesse a documentação completa em: http://localhost:8080/swagger-ui/index.html"
        );
    }
}
