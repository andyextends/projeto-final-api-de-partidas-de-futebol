package br.com.meli.apipartidafutebol.controller;
import br.com.meli.apipartidafutebol.domain.port.in.*;
import br.com.meli.apipartidafutebol.dto.*;
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
    @Autowired
    private ObjectMapper objectMapper;
    // Ports (use cases) mockados
    @MockBean
    private CadastrarClubeUseCase cadastrar;
    @MockBean
    private AtualizarClubeUseCase atualizar;
    @MockBean
    private ListarClubesUseCase listar;
    @MockBean
    private BuscarClubePorIdUseCase buscarPorId;
    @MockBean
    private DeletarClubeUseCase deletar;
    @MockBean
    private FiltrarClubesUseCase filtrar;
    @MockBean
    private ObterRetrospectoClubeUseCase obterRetrospecto;

    @Test
    void deveCadastrarNovoClube() throws Exception {
        var req = new ClubeRequestDto("Time Teste", "SP", LocalDate.of(2020, 1,
                1), true);
        var resp = new ClubeResponseDto(1L, "Time Teste", "SP", LocalDate.of(2020,
                1, 1), true);
        Mockito.when(cadastrar.executar(any(ClubeRequestDto.class))).thenReturn(resp);
        mockMvc.perform(post("/clube")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/clube/1")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Time Teste"))
                .andExpect(jsonPath("$.siglaEstado").value("SP"))
                .andExpect(jsonPath("$.ativo").value(true));
    }

    private String containsString(String path) {
        return path;
    }


    @Test
    void deveListarTodosClubes() throws Exception {
        var lista = List.of(
                new ClubeResponseDto(1L, "A", "SP", LocalDate.of(2020,
                        1,1), true),
                new ClubeResponseDto(2L, "B", "RJ", LocalDate.of(2021,
                        1,1), false)
        );
        Mockito.when(listar.executar()).thenReturn(lista);
        mockMvc.perform(get("/clube"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("A"))
                .andExpect(jsonPath("$[1].siglaEstado").value("RJ"));
    }
    @Test
    void deveBuscarPorId() throws Exception {
        var resp = new ClubeResponseDto(10L, "Time X", "MG", LocalDate.of(2019,
                5,10), true);
        Mockito.when(buscarPorId.executar(10L)).thenReturn(resp);
        mockMvc.perform(get("/clube/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.nome").value("Time X"));
    }
    @Test
    void deveAtualizar() throws Exception {
        var req = new ClubeRequestDto("Atualizado", "PR", LocalDate.of(2022,
                2,2), false);
        var resp = new ClubeResponseDto(5L, "Atualizado", "PR", LocalDate.of(2022,
                2,2), false);
        Mockito.when(atualizar.executar(eq(5L), any(ClubeRequestDto.class))).thenReturn(resp);
        mockMvc.perform(put("/clube/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.nome").value("Atualizado"))
                .andExpect(jsonPath("$.ativo").value(false));
    }
    @Test
    void deveDeletar() throws Exception {
        Mockito.doNothing().when(deletar).executar(3L);
        mockMvc.perform(delete("/clube/3"))
                .andExpect(status().isNoContent());
    }
    @Test
    void deveFiltrarComPaginacao() throws Exception {
        var filtro = new FiltroClubeRequestDto(); // preencha se tiver campos
        var page = new PageImpl<>(List.of(
                new ClubeResponseDto(1L, "Filtro FC", "CE", LocalDate.of(2021,
                        1,1), true)
        ), PageRequest.of(0,10, Sort.by("nome")), 1);
        Mockito.when(filtrar.executar(any(FiltroClubeRequestDto.class), any(Pageable.class)))
                .thenReturn(page);
        mockMvc.perform(post("/clube/filtro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filtro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].nome").value("Filtro FC"));
    }
    @Test
    void deveObterRetrospecto() throws Exception {
        var retro = new RetrospectoClubeDto();
        retro.setClubeId(7L);
        retro.setNomeClube("Teste");
        retro.setVitorias(4);
        retro.setEmpates(2);
        retro.setDerrotas(1);
        retro.setGolsMarcados(9);
        retro.setGolsSofridos(3);
        retro.setTotalJogos(7);
        Mockito.when(obterRetrospecto.executar(7L)).thenReturn(retro);
        mockMvc.perform(get("/clube/7/retrospecto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clubeId").value(7))
                .andExpect(jsonPath("$.nomeClube").value("Teste"))
                .andExpect(jsonPath("$.totalJogos").value(7));
    }
}






