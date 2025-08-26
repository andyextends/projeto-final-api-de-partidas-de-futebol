package br.com.meli.apipartidafutebol.adapter.in.web;

import br.com.meli.apipartidafutebol.dto.EstadioRequestDto;
import br.com.meli.apipartidafutebol.dto.EstadioResponseDto;
import br.com.meli.apipartidafutebol.model.Estadio;

public class EstadioWebMapper {
    // DTO -> Entidade
    public static Estadio toDomain(EstadioRequestDto dto) {
        if (dto == null) return null;
        return new Estadio(
                dto.getNome(),
                dto.getCidade(),
                dto.getCapacidade(),
                dto.getAtivo(),
                dto.getCep()
        );
    }
    // Entidade -> DTO
    public static EstadioResponseDto toDto(Estadio estadio) {
        if (estadio == null) return null;
        return new EstadioResponseDto(estadio);
    }
}
