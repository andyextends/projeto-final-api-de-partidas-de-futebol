package br.com.meli.apipartidafutebol.controller;
import br.com.meli.apipartidafutebol.domain.port.in.*;
import br.com.meli.apipartidafutebol.dto.FiltroPartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import br.com.meli.apipartidafutebol.validator.PartidaValidator;
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
    private final PartidaValidator validator;
    private final CadastrarPartidaUseCase cadastrar;
    private final PublicarPartidaUseCase publicar;        // via Kafka
    private final ListarPartidasUseCase listar;
    private final BuscarPartidaPorIdUseCase buscarPorId;
    private final AtualizarPartidaUseCase atualizar;
    private final DeletarPartidaUseCase deletar;
    private final FiltrarPartidasUseCase filtrar;
    public PartidaController(
            PartidaValidator validator,
            CadastrarPartidaUseCase cadastrar,
            PublicarPartidaUseCase publicar,
            ListarPartidasUseCase listar,
            BuscarPartidaPorIdUseCase buscarPorId,
            AtualizarPartidaUseCase atualizar,
            DeletarPartidaUseCase deletar,
            FiltrarPartidasUseCase filtrar) {
        this.validator = validator;
        this.cadastrar = cadastrar;
        this.publicar = publicar;
        this.listar = listar;
        this.buscarPorId = buscarPorId;
        this.atualizar = atualizar;
        this.deletar = deletar;
        this.filtrar = filtrar;
    }
    @PostMapping("/salvar")
    @Operation(summary = "Cadastrar uma nova partida diretamente no banco")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Partida cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regras de negócio violadas")
    })
    public ResponseEntity<PartidaResponseDto> cadastrarDireto(@RequestBody @Valid PartidaRequestDto dto) {
        validator.validar(dto);
        PartidaResponseDto response = cadastrar.executar(dto);
        return ResponseEntity.created(URI.create("/partidas/" + response.getId())).body(response);
    }
    @PostMapping("/publicar")
    @Operation(summary = "Cadastrar uma nova partida (via Kafka)")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Partida enviada com sucesso para processamento"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regras de negócio violadas")
    })
    public ResponseEntity<Void> cadastrarPartida(@RequestBody @Valid PartidaRequestDto dto) {
        validator.validar(dto);
        publicar.executar(dto);
        return ResponseEntity.accepted().build();
    }
    @GetMapping
    @Operation(summary = "Listar todas as partidas salvas")
    @ApiResponse(responseCode = "200", description = "Lista de partidas retornada com sucesso")
    public ResponseEntity<List<PartidaResponseDto>> listarTodas() {
        return ResponseEntity.ok(listar.executar());
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar partida por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partida encontrada"),
            @ApiResponse(responseCode = "404", description = "Partida não encontrada")
    })
    public ResponseEntity<PartidaResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(buscarPorId.executar(id));
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
        validator.validar(dto);
        return ResponseEntity.ok(atualizar.executar(id, dto));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar partida por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Partida deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Partida não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletar.executar(id);
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
        return ResponseEntity.ok(filtrar.executar(filtro, pageable));
    }
}