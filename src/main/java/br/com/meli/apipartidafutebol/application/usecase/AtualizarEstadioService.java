package br.com.meli.apipartidafutebol.application.usecase;

import br.com.meli.apipartidafutebol.domain.port.in.AtualizarEstadioUseCase;
import br.com.meli.apipartidafutebol.domain.port.out.EnderecoPorCepPort;
import br.com.meli.apipartidafutebol.domain.port.out.EstadioRepositoryPort;
import br.com.meli.apipartidafutebol.dto.EstadioRequestDto;
import br.com.meli.apipartidafutebol.model.Estadio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class AtualizarEstadioService implements AtualizarEstadioUseCase {
    private final EstadioRepositoryPort estadioRepo;
    private final EnderecoPorCepPort enderecoPort;
    public AtualizarEstadioService(EstadioRepositoryPort estadioRepo, EnderecoPorCepPort enderecoPort) {
        this.estadioRepo = estadioRepo;
        this.enderecoPort = enderecoPort;
    }
    @Transactional
    @Override
    public Estadio executar(Long id, EstadioRequestDto dto) {
        Estadio estadio = estadioRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estádio não encontrado"));
        // DTO usa Lombok @Data -> getters padrão
        estadio.setNome(dto.getNome());
        // mantenha estas linhas somente se sua entidade tiver os campos:
        try { estadio.getClass().getDeclaredField("capacidade"); estadio.setCapacidade(dto.getCapacidade()); } catch (NoSuchFieldException ignored) {}
        try { estadio.getClass().getDeclaredField("ativo"); estadio.setAtivo(dto.getAtivo()); } catch (NoSuchFieldException ignored) {}
        try { estadio.getClass().getDeclaredField("cep"); estadio.setCep(dto.getCep()); } catch (NoSuchFieldException ignored) {}
        var end = enderecoPort.buscar(dto.getCep());
        String cidade = end.cidade();
        if (estadioRepo.existsByNomeAndCidade(estadio.getNome(), cidade.toUpperCase())) {
            throw new IllegalArgumentException("Já existe estádio com esse nome nessa cidade.");
        }
        // precisa existir setter de cidade na entidade
        estadio.setCidade(cidade);
        return estadioRepo.save(estadio);
    }
}
