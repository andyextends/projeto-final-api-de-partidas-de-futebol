package br.com.meli.apipartidafutebol.dto;

import br.com.meli.apipartidafutebol.model.Clube;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubeResponseDto {
    private Long id;
    private String nome;
    private String siglaEstado;
    private LocalDate dataCriacao;
    private Boolean ativo;


    public ClubeResponseDto(Clube clube) {
        this.id = clube.getId();
        this.nome = clube.getNome();
        this.siglaEstado = clube.getSiglaEstado();
        this.dataCriacao = clube.getDataCriacao();
        this.ativo = clube.getAtivo();
    }

}


  












