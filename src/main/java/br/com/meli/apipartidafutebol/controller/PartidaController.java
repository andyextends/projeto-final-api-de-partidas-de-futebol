package br.com.meli.apipartidafutebol.controller;
import br.com.meli.apipartidafutebol.dto.FiltroPartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import br.com.meli.apipartidafutebol.integration.PartidaProducer;
import br.com.meli.apipartidafutebol.service.PartidaService;
import br.com.meli.apipartidafutebol.validator.PartidaValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Partida", description = "Operações relacionadas às partidas de futebol")
@RestController
@RequestMapping("/partidas")
public class PartidaController {
    private final PartidaValidator partidaValidator;
    private final PartidaProducer partidaProducer;
    public PartidaController(PartidaValidator partidaValidator, PartidaProducer partidaProducer) {
        this.partidaValidator = partidaValidator;
        this.partidaProducer = partidaProducer;
    }
    @Autowired
    private PartidaService partidaService;


    @PostMapping("/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar uma nova partida diretamente no banco")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Partida cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regras de negócio violadas")
    })
    public PartidaResponseDto cadastrarDireto(@RequestBody @Valid PartidaRequestDto dto) {
        partidaValidator.validar(dto);
        return partidaService.salvar(dto);
    }
    @Operation(summary = "Cadastrar uma nova partida (via Kafka)")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Partida enviada com sucesso para processamento"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regras de negócio violadas")
    })
    @PostMapping("/publicar")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> cadastrarPartida(@RequestBody @Valid PartidaRequestDto dto)
            throws JsonProcessingException {
        // Validação da regra de negócio antes de enviar
        partidaValidator.validar(dto);
        // Serializar e enviar para o Kafka
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = mapper.writeValueAsString(dto);
        partidaProducer.enviarMensagem(json);
        return ResponseEntity.accepted().build();
    }
    @Operation(summary = "Listar todas as partidas salvas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de partidas retornada com sucesso")
    })
    @GetMapping
    public List<PartidaResponseDto> listarTodas() {
        return partidaService.listarTodas();
    }
    @Operation(summary = "Buscar partida por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partida encontrada"),
            @ApiResponse(responseCode = "404", description = "Partida não encontrada")
    })
    @GetMapping("/{id}")
    public PartidaResponseDto buscarPorId(@PathVariable Long id) {
        return partidaService.buscarPorId(id);
    }
    @Operation(summary = "Atualizar uma partida existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partida atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Partida não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regras de negócio violadas")
    })
    @PutMapping("/{id}")
    public PartidaResponseDto atualizar(@PathVariable Long id,
                                        @RequestBody @Valid PartidaRequestDto dto) {
        return partidaService.atualizar(id, dto);
    }
    @Operation(summary = "Deletar partida por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Partida deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Partida não encontrada")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        partidaService.deletar(id);
    }
    @Operation(summary = "Filtrar partidas com critérios avançados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partidas filtradas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição malformada")
    })
    @PostMapping("/filtrar")
    public Page<PartidaResponseDto> filtrarPartidas(@RequestBody FiltroPartidaRequestDto filtro,
                                                    @Parameter(hidden = true) Pageable pageable) {
        return partidaService.filtrarPartidas(filtro, pageable);
    }

}
