package com.weg.infoweg.modules.user.aplication.dtos;

import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserCreateRequest(UUID id,
                                @NotNull(message = "Username is required") String username,
                                @NotNull(message = "Email is required") String email,
                                @NotNull(message = "Password is required") @Size(min=8, message = "Password must be at least 8 characters") String password,
                                @NotNull(message = "Phone number is required") String phoneNumber,
                                @NotNull(message = "Access level is required") AccessLevel accessLevel) { }
