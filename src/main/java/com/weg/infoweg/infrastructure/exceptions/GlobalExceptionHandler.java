package com.weg.infoweg.infrastructure.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.weg.infoweg.infrastructure.api.dto.ResponseApiDto;
import com.weg.infoweg.modules.token.domain.exceptions.TokenException;
import com.weg.infoweg.modules.user.domain.exceptions.DomainException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ValidationException.class, DomainException.class, TokenException.class})
    public ResponseEntity<ResponseApiDto<Void>> handleBadRequestExceptions(Exception ex) {
        String errorCode = "";

        if (ex instanceof ValidationException) {
            errorCode = "VALIDATION_ERROR";
        } else if (ex instanceof DomainException) {
            errorCode = "DOMAIN_ERROR";
        } else if (ex instanceof TokenException) {
            errorCode = "TOKEN_ERROR";
        }

        return ResponseEntity.badRequest()
                .body(ResponseApiDto.error(ex.getMessage(), errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseApiDto<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest()
                .body(ResponseApiDto.error("Validation failed", "VALIDATION_ERROR", message));
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ResponseApiDto<Void>> handleAuthenticationException(InternalAuthenticationServiceException ex) {
        // Retorna o status 401 e uma mensagem de erro genérica por segurança
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseApiDto.error("Invalid credentials", "AUTHENTICATION_ERROR"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseApiDto<Void>> handleInternalServerErrors(Exception ex) {
        // Para qualquer exceção não tratada, retorne 500 para evitar expor detalhes internos.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseApiDto.error("An unexpected error occurred", "INTERNAL_SERVER_ERROR"));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, InvalidFormatException.class})
    public ResponseEntity<ResponseApiDto<Void>> handleJsonExceptions(Exception ex) {
        String message;
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) ex.getCause();
            // Verifica se a exceção é para o enum AccessLevel
            if (ife.getTargetType().isEnum()) {
                message = String.format(
                        "Invalid value for enum %s. Valid values are: %s",
                        ife.getTargetType().getSimpleName(),
                        java.util.Arrays.toString(ife.getTargetType().getEnumConstants())
                );
            } else {
                message = "Invalid JSON format or field type.";
            }
        } else {
            message = "Invalid JSON format in the request body.";
        }

        return ResponseEntity.badRequest()
                .body(ResponseApiDto.error(message, "INVALID_FORMAT_ERROR"));
    }


}