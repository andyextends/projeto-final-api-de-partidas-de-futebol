package br.com.meli.apipartidafutebol.controller;
import br.com.meli.apipartidafutebol.domain.port.in.*;
import br.com.meli.apipartidafutebol.dto.FiltroPartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import br.com.meli.apipartidafutebol.validator.PartidaValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(PartidaController.class)
class PartidaControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;
    // Controller depende de validator + ports → todos mockados aqui
    @MockBean private PartidaValidator validator;
    @MockBean private CadastrarPartidaUseCase cadastrar;
    @MockBean private PublicarPartidaUseCase publicar;
    @MockBean private ListarPartidasUseCase listar;
    @MockBean private BuscarPartidaPorIdUseCase buscarPorId;
    @MockBean private AtualizarPartidaUseCase atualizar;
    @MockBean private DeletarPartidaUseCase deletar;
    @MockBean private FiltrarPartidasUseCase filtrar;
    private PartidaRequestDto reqPadrao() {
        return new PartidaRequestDto(
                1L,                 // clubeMandanteId
                2L,                 // clubeVisitanteId
                3L,                 // estadioId
                LocalDateTime.of(2025, 8, 27, 20, 30, 0), // dataHora
                0,                  // placarMandante
                0                   // placarVisitante
        );
    }
    @Test
    void deveCadastrarDireto() throws Exception {
        var req = reqPadrao();
        var resp = new PartidaResponseDto(
                10L, "São Paulo", "Corinthians", "Morumbi",
                req.getDataHora(), 0, 0
        );
        Mockito.doNothing().when(validator).validar(any());
        Mockito.when(cadastrar.executar(any())).thenReturn(resp);
        mockMvc.perform(post("/partida/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.clubeMandante").value("São Paulo"))
                .andExpect(jsonPath("$.clubeVisitante").value("Corinthians"))
                .andExpect(jsonPath("$.estadio").value("Morumbi"));
    }
    @Test
    void devePublicarPartida() throws Exception {
        var req = reqPadrao();
        Mockito.doNothing().when(validator).validar(any());
        Mockito.doNothing().when(publicar).executar(any());
        mockMvc.perform(post("/partida/publicar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isAccepted());
    }
    @Test
    void deveListarTodas() throws Exception {
        var a = new PartidaResponseDto(1L, "SPFC", "Palmeiras", "Morumbi",
                LocalDateTime.now(), 1, 0);
        var b = new PartidaResponseDto(2L, "Corinthians", "Santos", "Neo Química",
                LocalDateTime.now().plusDays(1), 2, 2);
        Mockito.when(listar.executar()).thenReturn(List.of(a, b));
        mockMvc.perform(get("/partida"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
    @Test
    void deveBuscarPorId() throws Exception {
        var resp = new PartidaResponseDto(7L, "Grêmio", "Inter", "Arena do Grêmio",
                LocalDateTime.now(), 3, 1);
        Mockito.when(buscarPorId.executar(7L)).thenReturn(resp);
        mockMvc.perform(get("/partida/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.clubeMandante").value("Grêmio"))
                .andExpect(jsonPath("$.clubeVisitante").value("Inter"));
    }
    @Test
    void deveAtualizar() throws Exception {
        var req = reqPadrao();
        var resp = new PartidaResponseDto(5L, "Cruzeiro", "Atlético", "Mineirão",
                req.getDataHora(), 1, 2);
        Mockito.doNothing().when(validator).validar(any());
        Mockito.when(atualizar.executar(eq(5L), any())).thenReturn(resp);
        mockMvc.perform(put("/partida/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.clubeMandante").value("Cruzeiro"))
                .andExpect(jsonPath("$.clubeVisitante").value("Atlético"));
    }
    @Test
    void deveDeletar() throws Exception {
        Mockito.doNothing().when(deletar).executar(9L);
        mockMvc.perform(delete("/partida/9"))
                .andExpect(status().isNoContent());
    }
    @Test
    void deveFiltrar() throws Exception {
        var filtro = new FiltroPartidaRequestDto(
                1L, 2L, 3L, LocalDate.of(2025, 8, 27), false, "MANDANTE"
        );
        Page<PartidaResponseDto> page = new PageImpl<>(
                List.of(new PartidaResponseDto(20L, "Bahia", "Vitória", "Fonte Nova",
                        LocalDateTime.now(), 2, 0))
        );
        Mockito.when(filtrar.executar(any(), any())).thenReturn(page);
        mockMvc.perform(post("/partida/filtrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(filtro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }
}