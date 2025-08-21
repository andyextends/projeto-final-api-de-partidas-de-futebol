package br.com.meli.apipartidafutebol.config;

import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import br.com.meli.apipartidafutebol.service.EmailService;
import br.com.meli.apipartidafutebol.service.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class RelatorioPartidasScheduler {
    @Autowired
    private PartidaService partidaService;
    @Autowired
    private EmailService emailService;

    @Value("${relatorio.email.destinatario}")
    private String destinatario;


    // Executa todos os dias às 08h00 da manhã (horário de São Paulo)
    @Scheduled(cron = "0 0 8 * * *", zone = "America/Sao_Paulo")
    public void enviarRelatorioPartidasAgendadas() {
        List<PartidaResponseDto> partidas = partidaService.listarTodas();
        if (partidas.isEmpty()) {
            System.out.println(" Nenhuma partida cadastrada no momento.");
            return;
        }
        String corpoEmail = gerarCorpoRelatorio(partidas);
        String assunto = " Relatório Diário de Partidas Cadastradas";
        try {
            emailService.enviarRelatorioPartidas(destinatario, assunto, corpoEmail);
            System.out.println(" Relatório de partidas enviado com sucesso.");
        } catch (Exception e) {
            System.err.println(" Erro ao enviar relatório de partidas: " + e.getMessage());
        }
    }
    private String gerarCorpoRelatorio(List<PartidaResponseDto> partidas) {
        return partidas.stream()
                .map(p -> String.format("<li><strong>ID:</strong> %d | <strong>Data:</strong> %s |" +
                                " <strong>Mandante:</strong> %s | <strong>Visitante:</strong> %s</li>",
                        p.getId(),
                        p.getDataHora(),
                        p.getClubeMandante(),
                        p.getClubeVisitante()))
                .collect(Collectors.joining("", "<ul>", "</ul>"));
    }
}