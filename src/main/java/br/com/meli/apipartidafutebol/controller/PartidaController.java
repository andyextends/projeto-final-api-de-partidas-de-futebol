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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
@Tag(name = "Partida", description = "Operações relacionadas às partidas de futebol")
@RestController
@RequestMapping("/partida")
public class PartidaController {
    private final PartidaValidator partidaValidator;
    private final PartidaProducer partidaProducer;
    private final PartidaService partidaService;
    public PartidaController(PartidaValidator partidaValidator,
                             PartidaProducer partidaProducer,
                             PartidaService partidaService) {
        this.partidaValidator = partidaValidator;
        this.partidaProducer = partidaProducer;
        this.partidaService = partidaService;
    }
    @PostMapping("/salvar")
    @Operation(summary = "Cadastrar uma nova partida diretamente no banco")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Partida cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regras de negócio violadas")
    })
    public ResponseEntity<PartidaResponseDto> cadastrarDireto(@RequestBody @Valid PartidaRequestDto dto) {
        partidaValidator.validar(dto);
        PartidaResponseDto response = partidaService.salvar(dto);
        return ResponseEntity.created(URI.create("/partidas/" + response.getId())).body(response);
    }
    @PostMapping("/publicar")
    @Operation(summary = "Cadastrar uma nova partida (via Kafka)")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Partida enviada com sucesso para processamento"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regras de negócio violadas")
    })
    public ResponseEntity<Void> cadastrarPartida(@RequestBody @Valid PartidaRequestDto dto)
            throws JsonProcessingException {
        partidaValidator.validar(dto);
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = mapper.writeValueAsString(dto);
        partidaProducer.enviarMensagem(json);
        return ResponseEntity.accepted().build();
    }
    @GetMapping
    @Operation(summary = "Listar todas as partidas salvas")
    @ApiResponse(responseCode = "200", description = "Lista de partidas retornada com sucesso")
    public ResponseEntity<List<PartidaResponseDto>> listarTodas() {
        return ResponseEntity.ok(partidaService.listarTodas());
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar partida por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partida encontrada"),
            @ApiResponse(responseCode = "404", description = "Partida não encontrada")
    })
    public ResponseEntity<PartidaResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.buscarPorId(id));
    }
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma partida existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partida atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Partida não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regras de negócio violadas")
    })
    public ResponseEntity<PartidaResponseDto> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid PartidaRequestDto dto) {
        return ResponseEntity.ok(partidaService.atualizar(id, dto));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar partida por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Partida deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Partida não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        partidaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/filtrar")
    @Operation(summary = "Filtrar partidas com critérios avançados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partidas filtradas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição malformada")
    })
    public ResponseEntity<Page<PartidaResponseDto>> filtrarPartidas(
            @RequestBody FiltroPartidaRequestDto filtro,
            @Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(partidaService.filtrarPartidas(filtro, pageable));
    }
}