package br.com.meli.apipartidafutebol.adapter.in.web;

import br.com.meli.apipartidafutebol.dto.EstadioRequestDto;
import br.com.meli.apipartidafutebol.dto.EstadioResponseDto;
import br.com.meli.apipartidafutebol.model.Estadio;

public final class EstadioWebMapper {
    private EstadioWebMapper() {}
    public static Estadio toDomain(EstadioRequestDto dto) {

        return new Estadio(dto.getNome(), dto.getCidade(), dto.getCapacidade(), dto.getAtivo(), dto.getCep());
    }
    public static EstadioResponseDto toDto(Estadio estadio) {

        return  new EstadioResponseDto(estadio);
    }
}
