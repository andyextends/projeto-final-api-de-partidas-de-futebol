package br.com.meli.apipartidafutebol.exception;

public class EstadioNaoEncontradoException extends RuntimeException {
    public EstadioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
