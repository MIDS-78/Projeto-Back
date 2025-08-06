package com.weg.infoweg.modules.user.aplication.dtos;

import com.weg.infoweg.modules.user.domain.enums.AccessLevel;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserCreateResponse(UUID id,
                                String username,
                                String email,
                                String phoneNumber,
                                AccessLevel accessLevel,
                                int createdBy,
                                LocalDateTime createdAt,
                                int updateBy,
                                LocalDateTime updatedAt) { }
