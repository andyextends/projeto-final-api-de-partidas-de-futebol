package br.com.meli.apipartidafutebol.controller;

import br.com.meli.apipartidafutebol.dto.ClubeRequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clube")
public class ClubeController {

    @GetMapping
    public String getMessage(){
        return "Clube de Futebol";
    }

    @PostMapping
    public ClubeRequestDto cadastrar(@RequestBody ClubeRequestDto clubeRequestDto) {
        return clubeRequestDto;
    }

}
