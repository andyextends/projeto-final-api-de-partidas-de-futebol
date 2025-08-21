package br.com.meli.apipartidafutebol.controller;

import br.com.meli.apipartidafutebol.config.RelatorioPartidasScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/teste")
public class EmailController {
    @Autowired
    private RelatorioPartidasScheduler scheduler;
    @GetMapping("/enviar-relatorio")
    public ResponseEntity<String> testarEnvioManual() {
        scheduler.enviarRelatorioPartidasAgendadas();
        return ResponseEntity.ok(" Relatório de partidas enviado manualmente com sucesso.");
    }
}
