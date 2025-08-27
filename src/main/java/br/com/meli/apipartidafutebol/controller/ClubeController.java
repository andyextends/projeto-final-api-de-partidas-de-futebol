package br.com.meli.apipartidafutebol.controller;
import br.com.meli.apipartidafutebol.domain.port.in.*;
import br.com.meli.apipartidafutebol.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@Tag(name = "Clube", description = "Operações relacionadas aos clubes de futebol")
@RestController
@RequestMapping("clube") // mantém singular
public class ClubeController {
    private final CadastrarClubeUseCase cadastrar;
    private final AtualizarClubeUseCase atualizar;
    private final ListarClubesUseCase listar;
    private final BuscarClubePorIdUseCase buscarPorId;
    private final DeletarClubeUseCase deletar;
    private final FiltrarClubesUseCase filtrar;
    private final ObterRetrospectoClubeUseCase obterRetrospecto;
    public ClubeController(CadastrarClubeUseCase cadastrar,
                           AtualizarClubeUseCase atualizar,
                           ListarClubesUseCase listar,
                           BuscarClubePorIdUseCase buscarPorId,
                           DeletarClubeUseCase deletar,
                           FiltrarClubesUseCase filtrar,
                           ObterRetrospectoClubeUseCase obterRetrospecto) {
        this.cadastrar = cadastrar;
        this.atualizar = atualizar;
        this.listar = listar;
        this.buscarPorId = buscarPorId;
        this.deletar = deletar;
        this.filtrar = filtrar;
        this.obterRetrospecto = obterRetrospecto;
    }

    @Operation(summary = "Cadastrar um novo clube")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Clube cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Clube com mesmo nome e estado já existe"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ClubeResponseDto> cadastrar(@RequestBody @Valid ClubeRequestDto dto) {
        ClubeResponseDto resp = cadastrar.executar(dto);
        return ResponseEntity.created(URI.create("/clube/" + resp.getId())).body(resp);
    }
    @Operation(summary = "Listar todos os clubes")
    @ApiResponse(responseCode = "200", description = "Lista de clubes retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ClubeResponseDto>> listarTodosClubes() {
        return ResponseEntity.ok(listar.executar());
    }
    @Operation(summary = "Buscar clube por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clube encontrado"),
            @ApiResponse(responseCode = "404", description = "Clube não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClubeResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(buscarPorId.executar(id));
    }
    @Operation(summary = "Atualizar clube existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clube atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clube não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClubeResponseDto> atualizar(@PathVariable Long id,
                                                      @RequestBody @Valid ClubeRequestDto dto) {
        return ResponseEntity.ok(atualizar.executar(id, dto));
    }
    @Operation(summary = "Deletar clube por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Clube deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clube não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletar.executar(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Filtrar clubes com paginação")
    @ApiResponse(responseCode = "200", description = "Filtro aplicado com sucesso")
    @PostMapping("/filtro")
    public ResponseEntity<Page<ClubeResponseDto>> filtrarClubes(
            @RequestBody FiltroClubeRequestDto filtro,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(filtrar.executar(filtro, pageable));
    }
    @Operation(summary = "Obter retrospecto do clube")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retrospecto retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clube não encontrado")
    })
    @GetMapping("/{id}/retrospecto")
    public ResponseEntity<RetrospectoClubeDto> retrospecto(@PathVariable Long id) {
        return ResponseEntity.ok(obterRetrospecto.executar(id));
    }
}

