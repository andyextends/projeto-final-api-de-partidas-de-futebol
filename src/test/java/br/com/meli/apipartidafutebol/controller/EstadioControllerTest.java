package br.com.meli.apipartidafutebol.controller;
import br.com.meli.apipartidafutebol.domain.port.in.*;
import br.com.meli.apipartidafutebol.dto.EstadioRequestDto;
import br.com.meli.apipartidafutebol.model.Estadio;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(EstadioController.class)
class EstadioControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    // mocks dos use cases (ports de entrada)
    @MockBean CadastrarEstadioUseCase cadastrar;
    @MockBean AtualizarEstadioUseCase atualizar;
    @MockBean ListarEstadiosUseCase listar;
    @MockBean BuscarEstadioPorIdUseCase buscarPorId;
    @MockBean DeletarEstadioUseCase deletar;
    private Estadio novoEstadio() {
        Estadio e = new Estadio();
        e.setId(1L);
        e.setNome("Arena Teste");
        e.setCidade("SÃO PAULO");
        e.setCapacidade(50000);
        e.setAtivo(true);
        e.setCep("01001000");
        return e;
    }
    @Test
    void cadastrar_deveRetornar201() throws Exception {
        var req = new EstadioRequestDto("Arena Teste", "São Paulo", 50000, true, "01001000");
        Mockito.when(cadastrar.executar(any(Estadio.class))).thenReturn(novoEstadio());
        mockMvc.perform(post("/estadio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/estadio/1"));
    }
    @Test
    void listar_deveRetornar200() throws Exception {
        Mockito.when(listar.executar()).thenReturn(List.of(novoEstadio()));
        mockMvc.perform(get("/estadio"))
                .andExpect(status().isOk());
    }
    @Test
    void buscarPorId_deveRetornar200() throws Exception {
        Mockito.when(buscarPorId.executar(1L)).thenReturn(novoEstadio());
        mockMvc.perform(get("/estadio/1"))
                .andExpect(status().isOk());
    }
    @Test
    void atualizar_deveRetornar200() throws Exception {
        var req = new EstadioRequestDto("Arena X", "São Paulo", 60000, true, "01001000");
        Mockito.when(atualizar.executar(eq(1L), any(EstadioRequestDto.class))).thenReturn(novoEstadio());
        mockMvc.perform(put("/estadio/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }
    @Test
    void deletar_deveRetornar204() throws Exception {
        doNothing().when(deletar).executar(1L);
        mockMvc.perform(delete("/estadio/1"))
                .andExpect(status().isNoContent());
    }
}