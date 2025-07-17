package br.com.meli.apipartidafutebol.exception;

public class EstadioOcupadoException extends RuntimeException {
    public EstadioOcupadoException(String mensagem) {
        super(mensagem);
    }
}
