package br.com.meli.apipartidafutebol.service;


import br.com.meli.apipartidafutebol.dto.ClubeRequestDto;
import br.com.meli.apipartidafutebol.dto.ClubeResponseDto;
import br.com.meli.apipartidafutebol.dto.FiltroClubeRequestDto;
import br.com.meli.apipartidafutebol.dto.RetrospectoClubeDto;
import br.com.meli.apipartidafutebol.exception.ClubeNaoEncontradoException;
import br.com.meli.apipartidafutebol.exception.ClubesIguaisException;
import br.com.meli.apipartidafutebol.model.Clube;
import br.com.meli.apipartidafutebol.repository.ClubeRepository;
import br.com.meli.apipartidafutebol.repository.PartidaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClubeServiceTest {
    @InjectMocks
    private ClubeService clubeService;
    @Mock
    private ClubeRepository clubeRepository;
    @Mock
    private PartidaRepository partidaRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void salvar_DeveRetornarClubeResponseDto_QuandoSucesso() {
        LocalDate dataCriacao = LocalDate.of(2000, 1, 1);
        ClubeRequestDto dto = new ClubeRequestDto("Clube A", "sp",dataCriacao, true);
        when(clubeRepository.findByNomeAndSiglaEstado("Clube A", "sp"))
                .thenReturn(Optional.empty());
        Clube clubeSalvo = new Clube("Clube A", "sp", dataCriacao, true);
        clubeSalvo.setId(1L); // <--- ESSENCIAL para o DTO retornar corretamente
        when(clubeRepository.save(any(Clube.class))).thenReturn(clubeSalvo);
        ClubeResponseDto response = clubeService.salvar(dto);
        assertNotNull(response);
        assertEquals("Clube A", response.getNome());
        assertEquals("sp", response.getSiglaEstado());
        assertEquals(dataCriacao, response.getDataCriacao());
        assertTrue(response.getAtivo());
        verify(clubeRepository).save(any(Clube.class));
    }
    @Test
    void salvar_DeveLancarExcecao_QuandoClubeDuplicado() {

        ClubeRequestDto dto = new ClubeRequestDto("Clube A","SP",
                LocalDate.of(2000,1,1),true);
        Clube clubeExistente = new Clube("Clube A", "SP",
                LocalDate.of(2000, 1, 1), true);
        when(clubeRepository.findByNomeAndSiglaEstado("Clube A", "SP")).
                thenReturn(Optional.of(clubeExistente));
        ClubesIguaisException exception =
        assertThrows(ClubesIguaisException.class, () -> clubeService.salvar(dto));
        assertEquals("Já existe um clube com esse nome no mesmo estado.", exception.getMessage());
    }
    @Test
    void listarTodosClubes_DeveRetornarLista() {
        List<Clube> clubes = List.of(
                new Clube("Clube A", "SP", LocalDate.of(2000, 1, 1), true),
                new Clube("Clube B", "RJ", LocalDate.of(1995, 3, 15), true)
        );
        when(clubeRepository.findAll()).thenReturn(clubes);
        List<ClubeResponseDto> resultado = clubeService.listarTodosClubes();
        assertEquals(2, resultado.size());
        assertEquals("Clube A", resultado.get(0).getNome());
        verify(clubeRepository).findAll();
    }
    @Test
    void buscarPorId_DeveRetornarClubeResponseDto_QuandoEncontrado() {
        Clube clube = new Clube("Clube A", "SP", LocalDate.of(2000, 1, 1), true);
        when(clubeRepository.findById(1L)).thenReturn(Optional.of(clube));
        ClubeResponseDto response = clubeService.buscarPorId(1L);
        assertNotNull(response);
        assertEquals("Clube A", response.getNome());
    }
    @Test
    void buscarPorId_DeveLancarExcecao_QuandoNaoEncontrado() {
        when(clubeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ClubeNaoEncontradoException.class, () -> clubeService.buscarPorId(99L));
    }
    @Test
    void atualizar_DeveRetornarClubeAtualizado() {
        Long id = 1L;
        ClubeRequestDto dto = new ClubeRequestDto(
                "Clube A" ,"MG", LocalDate.of(2000,1,1), true);
        Clube clubeExistente = new Clube(
                "Clube Antigo", "SP",
                LocalDate.of(2000, 1, 1), true
        );
        clubeExistente.setId(id); // :marca_de_verificação_branca: Necessário para o DTO de resposta
        // Simula o clube retornado pelo findById
        when(clubeRepository.findById(id)).thenReturn(Optional.of(clubeExistente));
        // Simula o save retornando o clube atualizado com ID
        when(clubeRepository.save(any(Clube.class))).thenAnswer(invocation -> {
            Clube clubeAtualizado = invocation.getArgument(0);
            clubeAtualizado.setId(id); // :marca_de_verificação_branca: Simula ID após salvar
            return clubeAtualizado;
        });
        ClubeResponseDto response = clubeService.atualizar(id, dto);
        assertNotNull(response);
        assertEquals("Clube A", response.getNome());
        assertEquals("MG", response.getSiglaEstado());
        assertEquals(LocalDate.of(2000, 1, 1), response.getDataCriacao());
        assertTrue(response.getAtivo());
        verify(clubeRepository).save(clubeExistente);
    }
    @Test
    void deletar_DeveExcluirClube_QuandoIdExistente() {
        Long id = 1L;
        Clube clube = new Clube("Clube A", "SP", LocalDate.of(2000, 1, 1), true);
        when(clubeRepository.findById(id)).thenReturn(Optional.of(clube));
        doNothing().when(clubeRepository).delete(clube);
        clubeService.deletar(id);
        verify(clubeRepository).delete(clube);
    }
    @Test
    void deletar_DeveLancarExcecao_QuandoIdInexistente() {
        when(clubeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ClubeNaoEncontradoException.class, () -> clubeService.deletar(99L));
    }
    @Test
    void obterRetrospecto_DeveRetornarRetrospecto_QuandoIdValido() {
        Clube clube = new Clube("Clube A", "SP",
                LocalDate.of(2000, 1, 1), true);
        clube.setId(1L);
        when(clubeRepository.findById(1L)).thenReturn(Optional.of(clube));
        RetrospectoClubeDto resultado = clubeService.obterRetrospecto(1L);
        assertNotNull(resultado);
        assertEquals("Clube A", resultado.getNomeClube());
        assertEquals(0, resultado.getVitorias());
        assertEquals(0, resultado.getTotalJogos());
    }
    @Test
    void filtrarClubes_DeveRetornarPaginaComClubes() {
        FiltroClubeRequestDto filtro = new FiltroClubeRequestDto();
        Pageable paginacao = PageRequest.of(0, 10);
        Clube clube = new Clube("Clube A", "SP", LocalDate.of(2000, 1, 1), true);
        List<Clube> clubes = List.of(clube);
        Page<Clube> page = new PageImpl<>(clubes, paginacao, clubes.size());
        when(clubeRepository.findAll(any(Specification.class), eq(paginacao)))
                .thenReturn(page);
        Page<ClubeResponseDto> resultado = clubeService.filtrarClubes(filtro, paginacao);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("Clube A", resultado.getContent().get(0).getNome());
        assertEquals("SP", resultado.getContent().get(0).getSiglaEstado());
        verify(clubeRepository).findAll(any(Specification.class), eq(paginacao));
    }
}