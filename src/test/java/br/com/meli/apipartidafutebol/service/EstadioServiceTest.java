package br.com.meli.apipartidafutebol.service;

import br.com.meli.apipartidafutebol.dto.EstadioRequestDto;
import br.com.meli.apipartidafutebol.dto.EstadioResponseDto;
import br.com.meli.apipartidafutebol.exception.EstadioNaoEncontradoException;
import br.com.meli.apipartidafutebol.model.Estadio;
import br.com.meli.apipartidafutebol.repository.EstadioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class EstadioServiceTest {
    @InjectMocks
    private EstadioService estadioService;
    @Mock
    private EstadioRepository estadioRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void salvar_DeveRetornarEstadioResponseDto_QuandoSucesso() {
        EstadioRequestDto dto = new EstadioRequestDto("Maracana", "RJ", 80000, true
                );
        Estadio estadioSalvo = new Estadio("Maracana", "RJ", 80000, true);
        estadioSalvo.setId(1L); // :marca_de_verificação_branca: necessário para o DTO
        when(estadioRepository.save(any(Estadio.class))).thenReturn(estadioSalvo);
        EstadioResponseDto response = estadioService.salvar(dto);
        assertNotNull(response);
        assertEquals("Maracana", response.getNome());
        assertEquals("RJ", response.getCidade());
        assertEquals(80000, response.getCapacidade());
        verify(estadioRepository).save(any(Estadio.class));
    }
    @Test
    void listarTodos_DeveRetornarListaDeEstadios() {
        List<Estadio> estadios = List.of(
                new Estadio("Maracanã", "RJ", 80000, true),
                new Estadio("Morumbi", "SP", 67000, true)
        );
        when(estadioRepository.findAll()).thenReturn(estadios);
        List<EstadioResponseDto> resultado = estadioService.listarTodos();
        assertEquals(2, resultado.size());
        assertEquals("Maracanã", resultado.get(0).getNome());
        assertEquals("Morumbi", resultado.get(1).getNome());
        verify(estadioRepository).findAll();
    }
    @Test
    void buscarPorId_DeveRetornarEstadioResponseDto_QuandoEncontrado() {
        Estadio estadio = new Estadio("Maracanã", "RJ", 80000, true);
        estadio.setId(1L);
        when(estadioRepository.findById(1L)).thenReturn(Optional.of(estadio));
        EstadioResponseDto response = estadioService.buscarPorId(1L);
        assertNotNull(response);
        assertEquals("Maracanã", response.getNome());
    }
    @Test
    void buscarPorId_DeveLancarExcecao_QuandoNaoEncontrado() {
        when(estadioRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EstadioNaoEncontradoException.class, () -> estadioService.buscarPorId(99L));
    }
    @Test
    void atualizar_DeveRetornarEstadioAtualizado() {
        Long id = 1L;
        EstadioRequestDto dto = new EstadioRequestDto("Novo Maracanã", "RJ", 75000, true);
        Estadio estadioExistente = new Estadio("Maracanã", "RJ", 80000, true);
        estadioExistente.setId(id);
        when(estadioRepository.findById(id)).thenReturn(Optional.of(estadioExistente));
        when(estadioRepository.save(any(Estadio.class))).thenAnswer(invocation -> {
            Estadio estadioAtualizado = invocation.getArgument(0);
            estadioAtualizado.setId(id);
            return estadioAtualizado;
        });
        EstadioResponseDto response = estadioService.atualizar(id, dto);
        assertNotNull(response);
        assertEquals("Novo Maracanã", response.getNome());
        assertEquals(75000, response.getCapacidade());
        verify(estadioRepository).save(estadioExistente);
    }
    @Test
    void deletar_DeveRemoverEstadio_QuandoIdExistente() {
        Long id = 1L;
        Estadio estadio = new Estadio("Maracanã", "RJ", 80000,
                true);
        when(estadioRepository.findById(id)).thenReturn(Optional.of(estadio));
        doNothing().when(estadioRepository).delete(estadio);
        estadioService.deletar(id);
        verify(estadioRepository).delete(estadio);
    }
    @Test
    void deletar_DeveLancarExcecao_QuandoIdNaoEncontrado() {
        when(estadioRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EstadioNaoEncontradoException.class, () -> estadioService.deletar(99L));
    }
}









