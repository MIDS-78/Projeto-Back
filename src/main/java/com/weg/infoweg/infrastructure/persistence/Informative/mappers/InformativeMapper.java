package com.weg.infoweg.infrastructure.persistence.Informative.mappers;

import com.weg.infoweg.modules.informative.application.dtos.InformativeGetResponse;
import com.weg.infoweg.modules.informative.domain.Informative;
import org.springframework.stereotype.Component;

@Component
public class InformativeMapper {

    public InformativeGetResponse toResponse(Informative informative){
        return new InformativeGetResponse(
                informative.getId(),
                informative.getTitle(),
                informative.getDescription(),
                informative.getImage()
        );
    }
}
