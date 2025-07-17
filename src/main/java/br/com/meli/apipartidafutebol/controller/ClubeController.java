package br.com.meli.apipartidafutebol.controller;

import br.com.meli.apipartidafutebol.dto.ClubeRequestDto;
import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;
import br.com.meli.apipartidafutebol.service.ClubeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clubes")
public class ClubeController {

    private final ClubeService clubeService;

    public ClubeController(ClubeService clubeService) {
        this.clubeService = clubeService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClubeResponseDto cadastrar(@RequestBody @Valid ClubeRequestDto clubeRequestDto) {
        return clubeService.salvar(clubeRequestDto);
    }
    
    @GetMapping
    public List<ClubeResponseDto> listarTodosClubes(){
        return clubeService.listarTodosClubes();
    }

    @GetMapping("/{id}")
    public ClubeResponseDto buscarPorId(@PathVariable Long id) {
        return clubeService.buscarPorId(id);
    }
    @PutMapping("/{id}")
    public ClubeResponseDto atualizar(@PathVariable Long id,
                                      @RequestBody @Valid ClubeRequestDto dto) {
        return clubeService.atualizar(id, dto);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        clubeService.deletar(id);
    }

    @GetMapping("/filtro")
    public ResponseEntity<Page<ClubeResponseDto>> filtrarClubes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String siglaEstado,
            @RequestParam(required = false) Boolean ativo,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable
    ) {

        return ResponseEntity.ok(clubeService.filtrarClubes(nome,siglaEstado,ativo, pageable));
    }









}
