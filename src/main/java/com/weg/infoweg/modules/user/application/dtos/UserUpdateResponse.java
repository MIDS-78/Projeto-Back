package com.weg.infoweg.modules.user.application.dtos;

import java.util.UUID;

public record UserUpdateResponse(UUID id, String name, String email) {
}
