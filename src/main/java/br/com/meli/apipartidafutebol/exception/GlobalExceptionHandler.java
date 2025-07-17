package br.com.meli.apipartidafutebol.exception;

import br.com.meli.apipartidafutebol.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ClubeNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDto> handleClubeNaoEncontrado(ClubeNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(HttpStatus.NOT_FOUND.name(), ex.getMessage()));
    }
    @ExceptionHandler(ClubesIguaisException.class)
    public ResponseEntity<ErrorResponseDto> handleClubesIguais(ClubesIguaisException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
    }
    @ExceptionHandler(ClubesInativosException.class)
    public ResponseEntity<ErrorResponseDto> handleClubesInativos(ClubesInativosException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
    }
    @ExceptionHandler(DataInvalidaException.class)
    public ResponseEntity<ErrorResponseDto> handleDataInvalida(DataInvalidaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
    }
    @ExceptionHandler(EstadioNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDto> handleEstadioNaoEncontrado(EstadioNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(HttpStatus.NOT_FOUND.name(), ex.getMessage()));
    }
    @ExceptionHandler(EstadioOcupadoException.class)
    public ResponseEntity<ErrorResponseDto> handleEstadioOcupado(EstadioOcupadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDto(HttpStatus.CONFLICT.name(), ex.getMessage()));
    }
    @ExceptionHandler(IntervaloInvalidoException.class)
    public ResponseEntity<ErrorResponseDto> handleIntervaloInvalido(IntervaloInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
    }
    @ExceptionHandler(PartidaNaoEncontradaException.class)
    public ResponseEntity<ErrorResponseDto> handlePartidaNaoEncontrada(PartidaNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(HttpStatus.NOT_FOUND.name(), ex.getMessage()));
    }
    // Fallback genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto("ERRO_INTERNO", "Erro inesperado: " + ex.getMessage()));
    }
}












