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
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CadastrarEstadioServiceTest {
    @Mock
    EstadioRepositoryPort estadioRepo;
    @Mock
    EnderecoPorCepPort enderecoPort;
    @InjectMocks
    CadastrarEstadioService service;
    @Test
    void deveCadastrarComCepValido() {
        // arrange
        Estadio est = new Estadio();
        est.setNome("Morumbi");
        est.setCep("01001-000");
        when(enderecoPort.buscar("01001-000"))
                .thenReturn(new Endereco("Praça da Sé", "São Paulo", "SP"));
        when(estadioRepo.existsByNomeAndCidade("Morumbi", "SÃO PAULO"))
                .thenReturn(false);
        when(estadioRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        // act
        Estadio salvo = service.executar(est);
        // assert
        assertNotNull(salvo);
        assertEquals("São Paulo", salvo.getCidade());
        verify(estadioRepo).save(any());
    }
    @Test
    void deveFalharComCepInvalido() {
        Estadio est = new Estadio();
        est.setNome("Morumbi");
        est.setCep("00000000");
        when(enderecoPort.buscar("00000000"))
                .thenThrow(new IllegalArgumentException("CEP inválido"));
        assertThrows(IllegalArgumentException.class,
                () -> service.executar(est));
        verify(estadioRepo, never()).save(any());
    }
    @Test
    void deveFalharSeJaExistirMesmoNomeECidade() {
        Estadio est = new Estadio();
        est.setNome("Morumbi");
        est.setCep("01001-000");
        when(enderecoPort.buscar("01001-000"))
                .thenReturn(new Endereco("Praça da Sé", "São Paulo", "SP"));
        when(estadioRepo.existsByNomeAndCidade("Morumbi", "SÃO PAULO"))
                .thenReturn(true);
        assertThrows(IllegalArgumentException.class,
                () -> service.executar(est));
        verify(estadioRepo, never()).save(any());
    }
}