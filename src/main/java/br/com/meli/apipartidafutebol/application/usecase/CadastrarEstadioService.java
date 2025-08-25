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
        var end = enderecoPort.buscar(estadio.getCep());
        if (end != null) {
            estadio.setCidade(normalizar(end.cidade()));
        }
        if (estadioRepo.existsByNomeAndCidade(estadio.getNome(), estadio.getCidade())) {
            throw new IllegalArgumentException("Já existe estádio com esse nome nessa cidade.");
        }
        return estadioRepo.save(estadio);
    }

    private String normalizar(String s) {
        return s == null ? null : s.trim().toUpperCase();
    }
}
