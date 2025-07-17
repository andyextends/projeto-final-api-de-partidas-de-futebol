package br.com.meli.apipartidafutebol.exception;

public class PartidaNaoEncontradaException extends RuntimeException {
    public PartidaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
