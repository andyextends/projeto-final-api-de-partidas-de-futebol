package br.com.meli.apipartidafutebol.controller;

import br.com.meli.apipartidafutebol.adapter.in.web.EstadioWebMapper;
import br.com.meli.apipartidafutebol.domain.port.in.*;
import br.com.meli.apipartidafutebol.dto.EstadioRequestDto;
import br.com.meli.apipartidafutebol.dto.EstadioResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@Tag(name = "Estadio", description = "Operações relacionadas aos estádios de futebol")
@RestController
@RequestMapping("/estadio")
public class EstadioController {
    private final CadastrarEstadioUseCase cadastrar;
    private final AtualizarEstadioUseCase atualizar;
    private final ListarEstadiosUseCase listar;
    private final BuscarEstadioPorIdUseCase buscarPorId;
    private final DeletarEstadioUseCase deletar;
    public EstadioController(CadastrarEstadioUseCase cadastrar,
                             AtualizarEstadioUseCase atualizar,
                             ListarEstadiosUseCase listar,
                             BuscarEstadioPorIdUseCase buscarPorId,
                             DeletarEstadioUseCase deletar) {
        this.cadastrar = cadastrar;
        this.atualizar = atualizar;
        this.listar = listar;
        this.buscarPorId = buscarPorId;
        this.deletar = deletar;
    }
    @Operation(summary = "Cadastrar um novo estádio")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estádio cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Já existe um estádio com esse nome na mesma cidade"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<EstadioResponseDto> cadastrar(@RequestBody @Valid EstadioRequestDto dto) {
        var salvo = cadastrar.executar(EstadioWebMapper.toDomain(dto));
        var body = EstadioWebMapper.toDto(salvo);
        return ResponseEntity.created(URI.create("/estadio/" + body.getId())).body(body);
    }
    @Operation(summary = "Listar todos os estádios")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Lista de estádios retornada com sucesso") })
    @GetMapping
    public ResponseEntity<List<EstadioResponseDto>> listarTodos() {
        var lista = listar.executar().stream().map(EstadioWebMapper::toDto).toList();
        return ResponseEntity.ok(lista);
    }
    @Operation(summary = "Buscar estádio por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estádio encontrado"),
            @ApiResponse(responseCode = "404", description = "Estádio não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstadioResponseDto> buscarPorId(@PathVariable Long id) {
        var e = buscarPorId.executar(id);
        return ResponseEntity.ok(EstadioWebMapper.toDto(e));
    }
    @Operation(summary = "Atualizar estádio existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estádio atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estádio não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstadioResponseDto> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid EstadioRequestDto dto) {
        var atualizado = atualizar.executar(id, EstadioWebMapper.toDomain(dto));
        return ResponseEntity.ok(EstadioWebMapper.toDto(atualizado));
    }
    @Operation(summary = "Deletar estádio por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estádio deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estádio não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletar.executar(id);
        return ResponseEntity.noContent().build();
    }
}
