package br.com.meli.apipartidafutebol.controller;
import br.com.meli.apipartidafutebol.dto.EstadioRequestDto;
import br.com.meli.apipartidafutebol.dto.EstadioResponseDto;
import br.com.meli.apipartidafutebol.service.EstadioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(EstadioController.class)
class EstadioControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EstadioService estadioService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void cadastrar() throws Exception {
        EstadioRequestDto request = new EstadioRequestDto("Arena Teste", "São Paulo", 50000, true,"00000000");
        EstadioResponseDto response = new EstadioResponseDto();
        Mockito.when(estadioService.salvar(any())).thenReturn(response);
        mockMvc.perform(post("/estadios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void listarTodos() throws Exception {
        Mockito.when(estadioService.listarTodos()).thenReturn(List.of(new EstadioResponseDto()));
        mockMvc.perform(get("/estadios"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId() throws Exception {
        Mockito.when(estadioService.buscarPorId(1L)).thenReturn(new EstadioResponseDto());
        mockMvc.perform(get("/estadios/1"))
                .andExpect(status().isOk());
    }

    @Test
    void atualizar() throws Exception {
        EstadioRequestDto request = new EstadioRequestDto("Novo Nome", "Rio de Janeiro", 60000, true,"00000000");
        EstadioResponseDto response = new EstadioResponseDto();
        Mockito.when(estadioService.atualizar(Mockito.eq(1L), any())).thenReturn(response);
        mockMvc.perform(put("/estadios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deletar() throws Exception {
        doNothing().when(estadioService).deletar(1L);
        mockMvc.perform(delete("/estadios/1"))
                .andExpect(status().isNoContent());
    }
}