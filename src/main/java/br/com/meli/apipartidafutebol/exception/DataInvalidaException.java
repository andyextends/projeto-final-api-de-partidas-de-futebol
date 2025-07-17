package br.com.meli.apipartidafutebol.exception;

public class DataInvalidaException extends RuntimeException {
    public DataInvalidaException(String mensagem) {
        super(mensagem);
    }
}
