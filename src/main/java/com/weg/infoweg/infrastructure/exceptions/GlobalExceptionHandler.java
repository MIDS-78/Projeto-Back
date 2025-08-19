package com.weg.infoweg.infrastructure.exceptions;

import com.weg.infoweg.infrastructure.api.dto.ResponseApiDto;
import com.weg.infoweg.modules.token.domain.exceptions.TokenException;
import com.weg.infoweg.modules.user.domain.exceptions.DomainException;
import jakarta.validation.ValidationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ResponseApiDto<Void>> handlerExceptionsValidations(ValidationException ve){

        String message = ve.getMessage();

        return ResponseEntity.badRequest().body(new ResponseApiDto<>("error", message, "VALIDATION_ERROR", LocalDateTime.now()));
    }


    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ResponseApiDto<Void>> handlerExceptionsDomain(DomainException de){
        String message = de.getMessage();

        return ResponseEntity.badRequest().body(new ResponseApiDto<>("error", message, "DOMAIN_ERROR", LocalDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseApiDto<Void>> handlerExceptionMethod(MethodArgumentNotValidException ma){
        String message = ma.getMessage();
        return ResponseEntity.badRequest().body(new ResponseApiDto<Void>("error", message, "METHOD_ERROR", LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseApiDto<Void>> handlerException(Exception e){
        String message = e.getMessage();
        return ResponseEntity.badRequest().body(new ResponseApiDto<Void>("error", message, "EXCEPTION_ERROR", LocalDateTime.now()));
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ResponseApiDto<Void>> handlerTokenException(TokenException tokenException){
        String message = tokenException.getMessage();
        return ResponseEntity.badRequest().body(new ResponseApiDto<Void>("error", message, "TOKEN_ERROR", LocalDateTime.now()));
    }



}
