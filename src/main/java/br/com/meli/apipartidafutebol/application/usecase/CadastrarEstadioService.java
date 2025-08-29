package br.com.meli.apipartidafutebol.application.usecase;

import br.com.meli.apipartidafutebol.domain.port.in.CadastrarEstadioUseCase;
import br.com.meli.apipartidafutebol.domain.port.out.EnderecoPorCepPort;
import br.com.meli.apipartidafutebol.domain.port.out.EstadioRepositoryPort;
import br.com.meli.apipartidafutebol.model.Estadio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CadastrarEstadioService implements CadastrarEstadioUseCase {
    private final EstadioRepositoryPort estadioRepo;
    private final EnderecoPorCepPort enderecoPort;

    public CadastrarEstadioService(EstadioRepositoryPort estadioRepo, EnderecoPorCepPort enderecoPort) {
        this.estadioRepo = estadioRepo;
        this.enderecoPort = enderecoPort;
    }

    @Transactional
    @Override
    public Estadio executar(Estadio estadio) {
        // normalizações
        estadio.setNome(normalizar(estadio.getNome()));
        estadio.setCep(onlyDigits(estadio.getCep()));
        var end = enderecoPort.buscar(estadio.getCep());
        estadio.setCidade(normalizar(end.getCidade()));
        estadio.setUf(normalizar(end.getUf()));
        if (estadioRepo.existsByNomeAndCidade(estadio.getNome(), estadio.getCidade())) {
            throw new IllegalArgumentException("Já existe estádio com esse nome nessa cidade.");
        }
        return estadioRepo.save(estadio);
    }

    private String normalizar(String s) {
        return s == null ? null : s.trim().toUpperCase();
    }

    private String onlyDigits(String s) {
        return s == null ? null : s.replaceAll("[^0-9]", "");
    }
}