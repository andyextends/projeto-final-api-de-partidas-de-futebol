package br.com.meli.apipartidafutebol.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clube")
public class ClubeController {

    @GetMapping
    public String getMessage(){
        return "Clube de Futebol";
    }

}
