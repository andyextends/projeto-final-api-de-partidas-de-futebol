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
        // Valida e preenche cidade via CEP
        var end = enderecoPort.buscar(estadio.getCep());
        if (end != null) {
            estadio.setCidade(end.cidade());
        }
        return estadioRepo.save(estadio);
    }
}
