package br.com.meli.apipartidafutebol.application.usecase;

import br.com.meli.apipartidafutebol.domain.port.out.EnderecoPorCepPort;
import br.com.meli.apipartidafutebol.domain.port.out.EstadioRepositoryPort;
import br.com.meli.apipartidafutebol.domain.vo.Endereco;
import br.com.meli.apipartidafutebol.model.Estadio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CadastrarEstadioServiceTest {
    @Mock EstadioRepositoryPort repo;
    @Mock EnderecoPorCepPort enderecoPort;
    @InjectMocks CadastrarEstadioService service;
    @Test
    void deveCadastrar_ComCepValido_SemDuplicidade() {

        var novo = new Estadio("Arena X", null, 50_000, true, "01001-000");

        when(enderecoPort.buscar("01001000"))
                .thenReturn(new Endereco("Praça da Sé", "Sé", "São Paulo", "SP", "01001000"));

        when(repo.existsByNomeAndCidade("ARENA X", "SÃO PAULO")).thenReturn(false);

        when(repo.save(any(Estadio.class))).thenAnswer(inv -> inv.getArgument(0));

        var salvo = service.executar(novo);

        assertEquals("ARENA X", salvo.getNome());
        assertEquals("01001000", salvo.getCep());
        assertEquals("SÃO PAULO", salvo.getCidade());
        assertEquals("SP", salvo.getUf());
        assertEquals(50_000, salvo.getCapacidade());
        assertTrue(salvo.getAtivo());
        verify(enderecoPort).buscar("01001000");
        verify(repo).existsByNomeAndCidade("ARENA X", "SÃO PAULO");
        verify(repo).save(any(Estadio.class));
        verifyNoMoreInteractions(repo);
    }
    @Test
    void deveLancar_QuandoDuplicado() {
        var novo = new Estadio("Arena X", null, 40_000, true, "01001000");
        when(enderecoPort.buscar("01001000"))
                .thenReturn(new Endereco("Praça da Sé", "Sé", "São Paulo", "SP", "01001000"));

        when(repo.existsByNomeAndCidade("ARENA X", "SÃO PAULO")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.executar(novo));
        verify(enderecoPort).buscar("01001000");
        verify(repo).existsByNomeAndCidade("ARENA X", "SÃO PAULO");
        verify(repo, never()).save(any());
        verifyNoMoreInteractions(repo);
    }
    @Test
    void deveNormalizarNomeECep_AntesDeConsultarDuplicidade() {

        var novo = new Estadio("  arena x  ", null, 30_000, true, "01001-000");
        when(enderecoPort.buscar("01001000"))
                .thenReturn(new Endereco("Praça da Sé", "Sé", "São Paulo", "SP", "01001000"));
        when(repo.existsByNomeAndCidade("ARENA X", "SÃO PAULO")).thenReturn(false);
        when(repo.save(any(Estadio.class))).thenAnswer(inv -> inv.getArgument(0));
        var salvo = service.executar(novo);
        assertEquals("ARENA X", salvo.getNome());
        assertEquals("01001000", salvo.getCep());
        verify(repo).existsByNomeAndCidade("ARENA X", "SÃO PAULO");
        verify(repo).save(any(Estadio.class));
    }
}