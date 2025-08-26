package br.com.meli.apipartidafutebol.application.usecase;

import br.com.meli.apipartidafutebol.domain.port.out.EnderecoPorCepPort;
import br.com.meli.apipartidafutebol.domain.port.out.EstadioRepositoryPort;
import br.com.meli.apipartidafutebol.domain.vo.Endereco;
import br.com.meli.apipartidafutebol.dto.EstadioRequestDto;
import br.com.meli.apipartidafutebol.model.Estadio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class AtualizarEstadioServiceTest {
    @Mock EstadioRepositoryPort repo;
    @Mock EnderecoPorCepPort enderecoPort;
    @InjectMocks AtualizarEstadioService service;
    @Test
    void deveAtualizar_ComCepValido_SemDuplicidade() {

        var existente = new Estadio("Arena Y", "CIDADE ANTIGA", 40000, true, "00000000");
        existente.setId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(existente));

        when(enderecoPort.buscar("01001000"))
                .thenReturn(new Endereco("Praça da Sé", "Sé", "São Paulo", "SP", "01001000"));

        when(repo.existsByNomeAndCidade("ARENA X", "SÃO PAULO")).thenReturn(false);

        when(repo.save(any(Estadio.class))).thenAnswer(inv -> inv.getArgument(0));
        var dto = new EstadioRequestDto("Arena X", null, 50_000, true, "01001000");

        var atualizado = service.executar(1L, dto);

        assertEquals("ARENA X", atualizado.getNome());   // normalizado
        assertEquals(50_000, atualizado.getCapacidade());
        assertTrue(atualizado.getAtivo());
        assertEquals("01001000", atualizado.getCep());
        assertEquals("SÃO PAULO", atualizado.getCidade()); // cidade vinda do CEP e normalizada
        assertEquals("SP", atualizado.getUf());
        verify(repo).findById(1L);
        verify(enderecoPort).buscar("01001000");
        verify(repo).existsByNomeAndCidade("ARENA X", "SÃO PAULO");
        verify(repo).save(any(Estadio.class));
        verifyNoMoreInteractions(repo);
    }
    @Test
    void deveLancar_QuandoDuplicado() {
        var existente = new Estadio("Algum", "CIDADE", 30000, true, "00000000");
        existente.setId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(enderecoPort.buscar("01001000"))
                .thenReturn(new Endereco("Praça da Sé", "Sé", "São Paulo", "SP", "01001000"));

        when(repo.existsByNomeAndCidade("ARENA X", "SÃO PAULO")).thenReturn(true);
        var dto = new EstadioRequestDto("Arena X", null, 50_000, true, "01001000");
        assertThrows(IllegalArgumentException.class, () -> service.executar(1L, dto));
        verify(repo).findById(1L);
        verify(enderecoPort).buscar("01001000");
        verify(repo).existsByNomeAndCidade("ARENA X", "SÃO PAULO");
        verify(repo, never()).save(any());
        verifyNoMoreInteractions(repo);
    }
    @Test
    void deveLancar_QuandoNaoEncontrado() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        var dto = new EstadioRequestDto("Arena X", null, 50_000, true, "01001000");
        assertThrows(IllegalArgumentException.class, () -> service.executar(99L, dto));
        verify(repo).findById(99L);
        verifyNoMoreInteractions(repo);
        verifyNoInteractions(enderecoPort);
    }
}
