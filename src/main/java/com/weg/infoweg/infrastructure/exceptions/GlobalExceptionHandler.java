package com.weg.infoweg.infrastructure.exceptions;

import com.weg.infoweg.modules.user.domain.exceptions.DomainException;
import jakarta.validation.ValidationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Configuration
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handlerExceptionsValidations(ValidationException ve){

        String message = ve.getMessage();

        return ResponseEntity.badRequest().body(message);
    }


    @ExceptionHandler(DomainException.class)
    public ResponseEntity<?> handlerExceptionsDomain(DomainException de){
        String message = de.getMessage();

        return ResponseEntity.badRequest().body(message);
    }



}
