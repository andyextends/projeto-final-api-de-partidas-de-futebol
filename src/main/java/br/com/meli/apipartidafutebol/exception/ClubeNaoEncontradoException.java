package br.com.meli.apipartidafutebol.exception;

public class ClubeNaoEncontradoException extends RuntimeException {
    public ClubeNaoEncontradoException(String mensagem) {
        super( mensagem );
    }
}
