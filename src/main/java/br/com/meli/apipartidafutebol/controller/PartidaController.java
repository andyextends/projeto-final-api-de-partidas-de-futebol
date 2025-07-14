package br.com.meli.apipartidafutebol.controller;

import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import br.com.meli.apipartidafutebol.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/partidas")
public class PartidaController {
    @Autowired
    private PartidaService partidaService;
    // POST /partidas
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PartidaResponseDto cadastrar(@RequestBody @Valid PartidaRequestDto dto) {
        return partidaService.salvar(dto);
    }
    // GET /partidas
    @GetMapping
    public List<PartidaResponseDto> listarTodas() {
        return partidaService.listarTodas();
    }
    // GET /partidas/{id}
    @GetMapping("/{id}")
    public PartidaResponseDto buscarPorId(@PathVariable Long id) {
        return partidaService.buscarPorId(id);
    }
    // PUT /partidas/{id}
    @PutMapping("/{id}")
    public PartidaResponseDto atualizar(@PathVariable Long id,
                                        @RequestBody @Valid PartidaRequestDto dto) {
        return partidaService.atualizar(id, dto);
    }
    // DELETE /partidas/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        partidaService.deletar(id);
    }
}
