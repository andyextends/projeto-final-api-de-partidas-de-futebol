package br.com.meli.apipartidafutebol.exception;

public class EstadioIndisponivelException extends RuntimeException{
    public EstadioIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
