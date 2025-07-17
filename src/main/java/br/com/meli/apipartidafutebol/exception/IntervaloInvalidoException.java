package br.com.meli.apipartidafutebol.exception;

public class IntervaloInvalidoException extends RuntimeException {
    public IntervaloInvalidoException(String mensagem) {
        super(mensagem);
    }
}
