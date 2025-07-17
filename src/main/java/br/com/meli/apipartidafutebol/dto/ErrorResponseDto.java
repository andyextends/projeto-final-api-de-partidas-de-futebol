package br.com.meli.apipartidafutebol.dto;

public class ErrorResponseDto {
    private String status;
    private String mensagem;
    public ErrorResponseDto(String status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }
    public String getStatus() {
        return status;
    }
    public String getMensagem() {
        return mensagem;
    }

}
