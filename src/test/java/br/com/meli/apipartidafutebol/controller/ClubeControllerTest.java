package br.com.meli.apipartidafutebol.controller;
import br.com.meli.apipartidafutebol.dto.*;
import br.com.meli.apipartidafutebol.service.ClubeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(ClubeController.class)
class ClubeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClubeService clubeService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void deveCadastrarNovoClube() throws Exception {
        ClubeRequestDto requestDto = new ClubeRequestDto("Time Teste", "SP",
                LocalDate.of(2020, 1, 1), true);
        ClubeResponseDto responseDto = new ClubeResponseDto(1L, "Time Teste", "SP",
                LocalDate.of(2020, 1, 1), true);
        Mockito.when(clubeService.salvar(any())).thenReturn(responseDto);
        mockMvc.perform(post("/clubes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Time Teste"))
                .andExpect(jsonPath("$.siglaEstado").value("SP"))
                .andExpect(jsonPath("$.ativo").value(true));
    }
    @Test
    void deveListarTodosClubes() throws Exception {
        List<ClubeResponseDto> clubes = List.of(
                new ClubeResponseDto(1L, "Time A", "SP",
                        LocalDate.of(2020, 1, 1), true),
                new ClubeResponseDto(2L, "Time B", "RJ",
                        LocalDate.of(2021, 1, 1), true)
        );
        Mockito.when(clubeService.listarTodosClubes()).thenReturn(clubes);
        mockMvc.perform(get("/clubes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
    @Test
    void deveBuscarClubePorId() throws Exception {
        ClubeResponseDto responseDto = new ClubeResponseDto(1L, "Time X", "SP",
                LocalDate.of(2020, 1, 1), true);
        Mockito.when(clubeService.buscarPorId(1L)).thenReturn(responseDto);
        mockMvc.perform(get("/clubes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Time X"));
    }
    @Test
    void deveAtualizarClube() throws Exception {
        ClubeRequestDto requestDto = new ClubeRequestDto("Atualizado", "MG",
                LocalDate.of(2022, 1, 1), false);
        ClubeResponseDto responseDto = new ClubeResponseDto(1L, "Atualizado", "MG",
                LocalDate.of(2022, 1, 1), false);
        Mockito.when(clubeService.atualizar(eq(1L), any())).thenReturn(responseDto);
        mockMvc.perform(put("/clubes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Atualizado"))
                .andExpect(jsonPath("$.ativo").value(false));
    }
    @Test
    void deveDeletarClube() throws Exception {
        Mockito.doNothing().when(clubeService).deletar(1L);
        mockMvc.perform(delete("/clubes/1"))
                .andExpect(status().isNoContent());
    }
    @Test
    void deveFiltrarClubesComPaginacao() throws Exception {
        FiltroClubeRequestDto filtro = new FiltroClubeRequestDto(); // ajuste conforme campos reais
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nome"));
        Page<ClubeResponseDto> page = new PageImpl<>(List.of(
                new ClubeResponseDto(1L, "Filtro FC", "CE", LocalDate.of(2021, 1, 1), true)
        ));
        Mockito.when(clubeService.filtrarClubes(any(), any())).thenReturn(page);
        mockMvc.perform(post("/clubes/filtro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filtro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }
    @Test
    void deveObterRetrospectoClube() throws Exception {
        RetrospectoClubeDto retrospecto = new RetrospectoClubeDto();
        retrospecto.setClubeId(1L);
        retrospecto.setNomeClube("Clube Teste");
        retrospecto.setVitorias(5);
        retrospecto.setEmpates(3);
        retrospecto.setDerrotas(2);
        retrospecto.setGolsMarcados(12);
        retrospecto.setGolsSofridos(8);
        retrospecto.setTotalJogos(10);
        Mockito.when(clubeService.obterRetrospecto(1L)).thenReturn(retrospecto);
        mockMvc.perform(get("/clubes/1/retrospecto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clubeId").value(1L))
                .andExpect(jsonPath("$.nomeClube").value("Clube Teste"))
                .andExpect(jsonPath("$.vitorias").value(5))
                .andExpect(jsonPath("$.empates").value(3))
                .andExpect(jsonPath("$.derrotas").value(2))
                .andExpect(jsonPath("$.golsMarcados").value(12))
                .andExpect(jsonPath("$.golsSofridos").value(8))
                .andExpect(jsonPath("$.totalJogos").value(10));
    }

}