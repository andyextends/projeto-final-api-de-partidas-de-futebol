package br.com.meli.apipartidafutebol.exception;

public class ClubesInativosException extends RuntimeException {
    public ClubesInativosException(String mensagem) {
        super(mensagem);
    }
}
