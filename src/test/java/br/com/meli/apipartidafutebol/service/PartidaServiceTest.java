package br.com.meli.apipartidafutebol.service;

import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import br.com.meli.apipartidafutebol.exception.PartidaNaoEncontradaException;
import br.com.meli.apipartidafutebol.model.Clube;
import br.com.meli.apipartidafutebol.model.Estadio;
import br.com.meli.apipartidafutebol.model.Partida;
import br.com.meli.apipartidafutebol.repository.ClubeRepository;
import br.com.meli.apipartidafutebol.repository.EstadioRepository;
import br.com.meli.apipartidafutebol.repository.PartidaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PartidaServiceTest {
    @InjectMocks
    private PartidaService partidaService;
    @Mock
    private PartidaRepository partidaRepository;
    @Mock
    private ClubeRepository clubeRepository;
    @Mock
    private EstadioRepository estadioRepository;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void salvar_DeveRetornarPartidaResponseDto_QuandoSucesso() {
        // Arrange
        LocalDateTime dataHora = LocalDateTime.of(2025, 7, 20, 14, 0);
        Clube mandante = new Clube("Clube A", "SP",
                LocalDate.of(2000, 1, 1), true);
        mandante.setId(1L);
        Clube visitante = new Clube("Clube B", "RJ",
                LocalDate.of(2000, 1, 1), true);
        visitante.setId(2L);
        Estadio estadio = new Estadio("Arena", "SP", 50000, true);
        estadio.setId(3L);
        Partida partida = new Partida(mandante, visitante, estadio, dataHora, 1, 2);
        partida.setId(10L);
        when(clubeRepository.findById(1L)).thenReturn(Optional.of(mandante));
        when(clubeRepository.findById(2L)).thenReturn(Optional.of(visitante));
        when(estadioRepository.findById(3L)).thenReturn(Optional.of(estadio));
        when(partidaRepository.findAll()).thenReturn(Collections.emptyList());
        when(partidaRepository.save(any(Partida.class))).thenReturn(partida);
        PartidaRequestDto dto = new PartidaRequestDto(1L, 2L,
                dataHora, 3L, 1, 2);
        // Act
        PartidaResponseDto response = partidaService.salvar(dto);
        // Assert
        assertNotNull(response);
        assertEquals("Clube A", response.getClubeMandante());
        assertEquals("Clube B", response.getClubeVisitante());
        assertEquals("Arena", response.getEstadio());
        assertEquals(1, response.getPlacarMandante());
        assertEquals(2, response.getPlacarVisitante());
        verify(partidaRepository).save(any(Partida.class));
    }
    @Test
    void buscarPorId_DeveRetornarPartida_QuandoExiste() {
        Clube mandante = new Clube("Clube A", "SP", LocalDate.of(2000, 1, 1), true);
        mandante.setId(1L);
        Clube visitante = new Clube("Clube B", "RJ", LocalDate.of(2000, 1, 1), true);
        visitante.setId(2L);
        Estadio estadio = new Estadio("Arena", "SP", 50000, true);
        estadio.setId(3L);
        Partida partida = new Partida(
                mandante, visitante, estadio,
                LocalDateTime.of(2025, 7, 20, 18, 0),
                1, 1
        );
        partida.setId(1L);
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        PartidaResponseDto response = partidaService.buscarPorId(1L);
        assertNotNull(response);
        assertEquals("Clube A", response.getClubeMandante());
        assertEquals("Clube B", response.getClubeVisitante());
        assertEquals("Arena", response.getEstadio());
    }

    @Test
    void listarTodas_DeveRetornarListaDePartidas() {
        Clube mandante = new Clube("Clube A", "SP", LocalDate.of(2000, 1, 1), true);
        mandante.setId(1L);
        Clube visitante = new Clube("Clube B", "RJ", LocalDate.of(2000, 1, 1), true);
        visitante.setId(2L);
        Estadio estadio = new Estadio("Arena", "SP", 50000, true);
        estadio.setId(3L);
        Partida partida1 = new Partida(mandante, visitante, estadio,
                LocalDateTime.of(2025, 7, 20, 18, 0), 2, 2);
        partida1.setId(1L);
        Partida partida2 = new Partida(mandante, visitante, estadio,
                LocalDateTime.of(2025, 7, 22, 20, 0), 1, 0);
        partida2.setId(2L);
        when(partidaRepository.findAll()).thenReturn(List.of(partida1, partida2));
        List<PartidaResponseDto> partidas = partidaService.listarTodas();
        assertEquals(2, partidas.size());
        assertEquals("Clube A", partidas.get(0).getClubeMandante());
        assertEquals("Clube B", partidas.get(1).getClubeVisitante());
    }

    @Test
    void deletar_DeveRemoverPartida_QuandoExiste() {
        Partida partida = new Partida();
        partida.setId(5L);
        when(partidaRepository.findById(5L)).thenReturn(Optional.of(partida));
        partidaService.deletar(5L);
        verify(partidaRepository).delete(partida);
    }
    @Test
    void deletar_DeveLancarExcecao_QuandoNaoExiste() {
        when(partidaRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(PartidaNaoEncontradaException.class, () -> partidaService.deletar(999L));
    }
    @Test
    void atualizar_DeveRetornarPartidaAtualizada_QuandoValido() {
        LocalDateTime dataHora = LocalDateTime.of(2025, 7, 21, 16, 0);
        Clube mandante = new Clube("Clube A", "SP",
                LocalDate.of(2000, 1, 1), true);
        mandante.setId(1L);
        Clube visitante = new Clube("Clube B", "RJ",
                LocalDate.of(2000, 1, 1), true);
        visitante.setId(2L);
        Estadio estadio = new Estadio("Arena", "SP", 50000, true);
        estadio.setId(3L);
        Partida partida = new Partida(mandante, visitante, estadio,
                dataHora.minusDays(1), 0, 0);
        partida.setId(1L);
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(clubeRepository.findById(1L)).thenReturn(Optional.of(mandante));
        when(clubeRepository.findById(2L)).thenReturn(Optional.of(visitante));
        when(estadioRepository.findById(3L)).thenReturn(Optional.of(estadio));
        when(partidaRepository.findAll()).thenReturn(Collections.emptyList());
        when(partidaRepository.save(any(Partida.class))).thenReturn(partida);
        PartidaRequestDto dto = new PartidaRequestDto(1L, 2L,
                dataHora, 3L, 2, 1);
        PartidaResponseDto response = partidaService.atualizar(1L, dto);
        assertNotNull(response);
        assertEquals("Clube A", response.getClubeMandante());
        assertEquals("Clube B", response.getClubeVisitante());
        assertEquals(2, response.getPlacarMandante());
        assertEquals(1, response.getPlacarVisitante());
    }
}








