package br.com.meli.apipartidafutebol.controller;

import br.com.meli.apipartidafutebol.dto.EstadioRequestDto;
import br.com.meli.apipartidafutebol.dto.EstadioResponseDto;
import br.com.meli.apipartidafutebol.service.EstadioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/estadios")
public class EstadioController {
    @Autowired
    private EstadioService estadioService;
    // POST /estadios
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadioResponseDto cadastrar(@RequestBody @Valid EstadioRequestDto dto) {
        return estadioService.salvar(dto);
    }
    // GET /estadios
    @GetMapping
    public List<EstadioResponseDto> listarTodos() {
        return estadioService.listarTodos();
    }
    // GET /estadios/{id}
    @GetMapping("/{id}")
    public EstadioResponseDto buscarPorId(@PathVariable Long id) {
        return estadioService.buscarPorId(id);
    }
    // PUT /estadios/{id}
    @PutMapping("/{id}")
    public EstadioResponseDto atualizar(@PathVariable Long id,
                                        @RequestBody @Valid EstadioRequestDto dto) {
        return estadioService.atualizar(id, dto);
    }
    // DELETE /estadios/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        estadioService.deletar(id);
    }
}
