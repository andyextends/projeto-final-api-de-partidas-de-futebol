package br.com.meli.apipartidafutebol.dto;

import br.com.meli.apipartidafutebol.model.Estadio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadioResponseDto {
    private Long id;
    private String nome;
    private String cidade;
    private Integer capacidade;
    private Boolean ativo;
    private String cep;

    // Construtor que recebe a entidade Estadio
    public EstadioResponseDto(Estadio estadio) {
        this(
                estadio.getId(),
                estadio.getNome(),
                estadio.getCidade(),
                estadio.getCapacidade(),
                estadio.getAtivo(),
                estadio.getCep()
        );
    }


}
