package com.weg.infoweg.modules.informative.application.dtos;

import java.util.UUID;

public record InformativeGetResponse(UUID id, String title, String description, byte[] image) {
}
