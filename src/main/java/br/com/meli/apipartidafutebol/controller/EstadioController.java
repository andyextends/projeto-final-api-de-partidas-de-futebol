package br.com.meli.apipartidafutebol.controller;

import br.com.meli.apipartidafutebol.dto.EstadioRequestDto;
import br.com.meli.apipartidafutebol.dto.EstadioResponseDto;
import br.com.meli.apipartidafutebol.service.EstadioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Estadio", description = "Operações relacionadas aos estadios de futebol")
@RestController
@RequestMapping("/estadios")
public class EstadioController {
    @Autowired
    private EstadioService estadioService;
    @Operation(summary = "Cadastrar um novo estádio")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estádio cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Já existe um estádio com esse nome na mesma cidade"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadioResponseDto cadastrar(@RequestBody @Valid EstadioRequestDto dto) {
        return estadioService.salvar(dto);
    }
    @Operation(summary = "Listar todos os estádios")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de estádios retornada com sucesso")
    })
    @GetMapping
    public List<EstadioResponseDto> listarTodos() {
        return estadioService.listarTodos();
    }
    @Operation(summary = "Buscar estádio por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estádio encontrado"),
            @ApiResponse(responseCode = "404", description = "Estádio não encontrado")
    })
    @GetMapping("/{id}")
    public EstadioResponseDto buscarPorId(@PathVariable Long id) {
        return estadioService.buscarPorId(id);
    }
    @Operation(summary = "Atualizar estádio existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estádio atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estádio não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public EstadioResponseDto atualizar(@PathVariable Long id,
                                        @RequestBody @Valid EstadioRequestDto dto) {
        return estadioService.atualizar(id, dto);
    }
    @Operation(summary = "Deletar estádio por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estádio deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estádio não encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        estadioService.deletar(id);
    }
}
