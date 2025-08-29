package br.com.meli.apipartidafutebol.application.usecase;

import br.com.meli.apipartidafutebol.domain.port.in.DeletarEstadioUseCase;
import br.com.meli.apipartidafutebol.domain.port.out.EstadioRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class DeletarEstadioService implements DeletarEstadioUseCase {
    private final EstadioRepositoryPort repo;
    public DeletarEstadioService(EstadioRepositoryPort repo) { this.repo = repo; }
    @Transactional
    @Override public void executar(Long id) {
        var estadio = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Estádio não encontrado"));
        // aguardar  precisar de validações de domínio antes de excluir, coloque aqui
        repo.save(estadio);
        // opcional se exclusão for lógica;
        // se for física, use springData.deleteById
        // no adapter criando método no port
        // estamos verificando caso exclusão for física: adicione no port `void deleteById(Long id);` e implemente no adapter chamando `springData.deleteById(id)`.
    }
}
