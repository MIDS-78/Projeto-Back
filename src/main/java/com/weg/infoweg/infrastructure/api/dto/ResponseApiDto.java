package com.weg.infoweg.infrastructure.api.dto;

import java.time.LocalDateTime;

public record ResponseApiDto<T>(String status, String message, String errorCode, T data, LocalDateTime localStamp) {

    /**
     * Cria uma resposta de sucesso sem dados (e.g., para operações de exclusão).
     * @param message Mensagem de sucesso.
     * @return ResponseApiDto com status "success".
     */
    public static ResponseApiDto<Void> success(String message) {
        return new ResponseApiDto<>("success", message, null, null, LocalDateTime.now());
    }

    /**
     * Cria uma resposta de sucesso com dados.
     * @param message Mensagem de sucesso.
     * @param data Dados do payload.
     * @return ResponseApiDto com status "success".
     * @param <T> Tipo de dados do payload.
     */
    public static <T> ResponseApiDto<T> success(String message, T data) {
        return new ResponseApiDto<>("success", message, null, data, LocalDateTime.now());
    }

    /**
     * Cria uma resposta de erro com mensagem de erro e código de erro.
     * @param message Mensagem de erro.
     * @param errorCode Código de erro específico.
     * @return ResponseApiDto com status "error".
     */
    public static ResponseApiDto<Void> error(String message, String errorCode) {
        return new ResponseApiDto<>("error", message, errorCode, null, LocalDateTime.now());
    }

    /**
     * Cria uma resposta de erro com dados (útil para retornar detalhes de validação).
     * @param message Mensagem de erro.
     * @param errorCode Código de erro específico.
     * @param data Detalhes de erro.
     * @return ResponseApiDto com status "error".
     * @param <T> Tipo de dados do payload.
     */
    public static <T> ResponseApiDto<T> error(String message, String errorCode, T data) {
        return new ResponseApiDto<>("error", message, errorCode, data, LocalDateTime.now());
    }
}
