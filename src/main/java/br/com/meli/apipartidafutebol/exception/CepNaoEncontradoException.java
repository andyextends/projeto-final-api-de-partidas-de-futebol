package br.com.meli.apipartidafutebol.exception;

public class CepNaoEncontradoException extends RuntimeException {
    public CepNaoEncontradoException(String message) {
        super("CEP não encontrado ou inválido "+ message);
    }
}
