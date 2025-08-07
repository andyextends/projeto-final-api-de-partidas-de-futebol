package br.com.meli.apipartidafutebol.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Environment env;
    public void enviarRelatorioPartidas(String destinatario, String assunto, String corpoHtml) throws MessagingException {
        if (corpoHtml == null || corpoHtml.isBlank()) {
            throw new IllegalArgumentException("O conteúdo do relatório está vazio.");
        }
        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");
        helper.setTo(destinatario);
        helper.setSubject(assunto);
        helper.setText(corpoHtml, true); // true = envia como HTML
        // Define o remetente se necessário
        String remetente = env.getProperty("spring.mail.username");
        if (remetente != null && !remetente.isBlank()) {
            helper.setFrom(remetente);
        }
        mailSender.send(mensagem);
    }
}
