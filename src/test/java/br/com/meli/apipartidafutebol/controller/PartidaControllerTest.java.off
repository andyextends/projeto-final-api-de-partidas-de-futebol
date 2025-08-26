package br.com.meli.apipartidafutebol.controller;
import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import br.com.meli.apipartidafutebol.dto.PartidaResponseDto;
import br.com.meli.apipartidafutebol.service.PartidaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(PartidaController.class)
class PartidaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PartidaService partidaService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void cadastrar() throws Exception {
        PartidaRequestDto request = new PartidaRequestDto(1L, 2L, LocalDateTime.now(), 1L, 3, 1);
        PartidaResponseDto response = new PartidaResponseDto();
        Mockito.when(partidaService.salvar(any())).thenReturn(response);
        mockMvc.perform(post("/partidas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
    @Test
    void listarTodas() throws Exception {
        Mockito.when(partidaService.listarTodas()).thenReturn(List.of(new PartidaResponseDto()));
        mockMvc.perform(get("/partidas"))
                .andExpect(status().isOk());
    }
    @Test
    void buscarPorId() throws Exception {
        Mockito.when(partidaService.buscarPorId(1L)).thenReturn(new PartidaResponseDto());
        mockMvc.perform(get("/partidas/1"))
                .andExpect(status().isOk());
    }
    @Test
    void atualizar() throws Exception {
        PartidaRequestDto request = new PartidaRequestDto(1L, 2L, LocalDateTime.now(), 1L, 2, 0);
        PartidaResponseDto response = new PartidaResponseDto();
        Mockito.when(partidaService.atualizar(Mockito.eq(1L), any())).thenReturn(response);
        mockMvc.perform(put("/partidas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
    @Test
    void deletar() throws Exception {
        mockMvc.perform(delete("/partidas/1"))
                .andExpect(status().isNoContent());
    }
    @Test
    void filtrarPartidas() throws Exception {
        Mockito.when(partidaService.filtrarPartidas(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(new PartidaResponseDto())));
        mockMvc.perform(post("/partidas/filtrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // filtro vazio
                .andExpect(status().isOk());
    }
}