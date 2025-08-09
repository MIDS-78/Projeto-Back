package com.weg.infoweg.infrastructure.api.dto;

import java.time.LocalDateTime;

public record ResponseApiDto<T>(String status, String message, String errorCode, T data, LocalDateTime localStamp){
    public ResponseApiDto(String status, String message, T data, LocalDateTime timestamp) {
        this(status, message, null, data, timestamp);
    }

    public ResponseApiDto(String status, String message, String errorCode, LocalDateTime timestamp) {
        this(status, message, errorCode, null, timestamp);
    }
}
