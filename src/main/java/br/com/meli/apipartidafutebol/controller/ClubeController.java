package br.com.meli.apipartidafutebol.controller;
import br.com.meli.apipartidafutebol.dto.ClubeRequestDto;
import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;
import br.com.meli.apipartidafutebol.dto.FiltroClubeRequestDto;
import br.com.meli.apipartidafutebol.dto.RetrospectoClubeDto;
import br.com.meli.apipartidafutebol.service.ClubeService;
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
@RequestMapping("clube")
public class ClubeController {
    private final ClubeService clubeService;
    public ClubeController(ClubeService clubeService) {
        this.clubeService = clubeService;
    }
    @Operation(summary = "Cadastrar um novo clube")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Clube cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Clube com mesmo nome e estado já existe"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ClubeResponseDto> cadastrar(@RequestBody @Valid ClubeRequestDto clubeRequestDto) {
        ClubeResponseDto response = clubeService.salvar(clubeRequestDto);
        return ResponseEntity.created(URI.create("/clube/" + response.getId())).body(response);
    }
    @Operation(summary = "Listar todos os clubes")
    @ApiResponse(responseCode = "200", description = "Lista de clubes retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ClubeResponseDto>> listarTodosClubes() {
        return ResponseEntity.ok(clubeService.listarTodosClubes());
    }
    @Operation(summary = "Buscar clube por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clube encontrado"),
            @ApiResponse(responseCode = "404", description = "Clube não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClubeResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clubeService.buscarPorId(id));
    }
    @Operation(summary = "Atualizar clube existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clube atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clube não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClubeResponseDto> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ClubeRequestDto dto
    ) {
        return ResponseEntity.ok(clubeService.atualizar(id, dto));
    }
    @Operation(summary = "Deletar clube por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Clube deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clube não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clubeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Filtrar clubes com paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filtro aplicado com sucesso")
    })
    @PostMapping("/filtro")
    public ResponseEntity<Page<ClubeResponseDto>> filtrarClubes(
            @RequestBody FiltroClubeRequestDto filtro,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable
    ) {
        return ResponseEntity.ok(clubeService.filtrarClubes(filtro, pageable));
    }
    @Operation(summary = "Obter retrospecto do clube")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retrospecto retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Clube não encontrado")
    })
    @GetMapping("/{id}/retrospecto")
    public ResponseEntity<RetrospectoClubeDto> retrospecto(@PathVariable Long id) {
        return ResponseEntity.ok(clubeService.obterRetrospecto(id));
    }
}
