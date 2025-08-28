package com.weg.infoweg.modules.user.application.dtos;

import com.weg.infoweg.modules.user.domain.enums.AccessLevel;

import java.util.UUID;

public record UserCreateResponse(UUID id,
                                String username,
                                String email,
                                String phoneNumber,
                                AccessLevel accessLevel){ }
