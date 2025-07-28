package br.com.meli.apipartidafutebol.validator;

import br.com.meli.apipartidafutebol.dto.PartidaRequestDto;
import org.springframework.stereotype.Component;
@Component
public class PartidaValidator {
    public void validar(PartidaRequestDto dto) {
        if (dto.getClubeMandanteId().equals(dto.getClubeVisitanteId())) {
            throw new IllegalArgumentException("Os clubes da partida devem ser diferentes.");
        }
        // Exemplo adicional: se quiser validar data no futuro
        /*
        if (dto.getDataPartida().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data da partida deve ser futura.");
        }
        */
    }
}










