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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(controllers = EstadioController.class)
class EstadioControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private CadastrarEstadioUseCase cadastrar;
    @MockBean private AtualizarEstadioUseCase atualizar;
    @MockBean private ListarEstadiosUseCase listar;
    @MockBean private BuscarEstadioPorIdUseCase buscarPorId;
    @MockBean private DeletarEstadioUseCase deletar;
    private Estadio estadio(Long id) {
        Estadio e = new Estadio();
        e.setId(id);
        e.setNome("Arena Teste");
        e.setCidade("São Paulo");
        return e;
    }
    @Test
    void cadastrar() throws Exception {
        var request = new EstadioRequestDto("Arena Teste","São Paulo",50000,true,"01001-000");
        Mockito.when(cadastrar.executar(any(Estadio.class))).thenReturn(estadio(1L));
        mockMvc.perform(post("/estadio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/estadio/1"));
    }
    @Test
    void listarTodos() throws Exception {
        Mockito.when(listar.executar()).thenReturn(List.of(estadio(1L)));
        mockMvc.perform(get("/estadio")).andExpect(status().isOk());
    }
    @Test
    void buscarPorId() throws Exception {
        Mockito.when(buscarPorId.executar(1L)).thenReturn(estadio(1L));
        mockMvc.perform(get("/estadio/1")).andExpect(status().isOk());
    }
    @Test
    void atualizar() throws Exception {
        var request = new EstadioRequestDto("Novo Nome","Rio de Janeiro",60000,true,"20040-020");
        Mockito.when(atualizar.executar(Mockito.eq(1L), any(EstadioRequestDto.class)))
                .thenReturn(estadio(1L));
        mockMvc.perform(put("/estadio/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
    @Test
    void deletar() throws Exception {
        Mockito.doNothing().when(deletar).executar(1L);
        mockMvc.perform(delete("/estadio/1")).andExpect(status().isNoContent());
    }
}