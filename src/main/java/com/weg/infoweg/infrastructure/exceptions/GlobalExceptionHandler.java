package com.weg.infoweg.infrastructure.exceptions;

import com.weg.infoweg.infrastructure.api.dto.ResponseApiDto;
import com.weg.infoweg.modules.user.domain.exceptions.DomainException;
import jakarta.validation.ValidationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Configuration
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



}
