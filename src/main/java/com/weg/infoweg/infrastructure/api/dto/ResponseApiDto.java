package com.weg.infoweg.infrastructure.api.dto;

import java.time.LocalDateTime;

public record ResponseApiDto<T>(String status, String message, String errorCode, T data, LocalDateTime localStamp){
}
