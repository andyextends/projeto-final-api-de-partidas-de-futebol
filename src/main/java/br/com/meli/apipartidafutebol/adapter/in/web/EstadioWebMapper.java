package br.com.meli.apipartidafutebol.adapter.in.web;

import br.com.meli.apipartidafutebol.dto.EstadioRequestDto;
import br.com.meli.apipartidafutebol.dto.EstadioResponseDto;
import br.com.meli.apipartidafutebol.model.Estadio;
public final class EstadioWebMapper {
    private EstadioWebMapper() {}
    public static Estadio toDomain(EstadioRequestDto dto) {
        var e = new Estadio();
        e.setNome(dto.getNome());
        e.setCep(dto.getCep());
        e.setCapacidade(dto.getCapacidade());
        e.setAtivo(dto.getAtivo());
        return e;
    }
    public static EstadioResponseDto toDto(Estadio e) {
        var r = new EstadioResponseDto();
        r.setId(e.getId());
        r.setNome(e.getNome());
        r.setCidade(e.getCidade());
        r.setCapacidade(e.getCapacidade());
        r.setAtivo(e.getAtivo());
        return r;
    }
}
