package br.com.meli.apipartidafutebol.controller;

import br.com.meli.apipartidafutebol.dto.ClubeRequestDto;
import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;
import br.com.meli.apipartidafutebol.service.ClubeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clubes")
public class ClubeController {

    private final ClubeService clubeService;

    public ClubeController(ClubeService clubeService) {
        this.clubeService = clubeService;
    }

    //@GetMapping
   // public String getMessage(){
    //    return "Clube de Futebol";
   // }

    @PostMapping
    public ClubeRequestDto cadastrar(@RequestBody ClubeRequestDto clubeRequestDto) {
        return clubeRequestDto;
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








}
