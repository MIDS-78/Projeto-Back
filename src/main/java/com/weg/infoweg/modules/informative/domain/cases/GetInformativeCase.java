package com.weg.infoweg.modules.informative.domain.cases;

import com.weg.infoweg.modules.informative.application.dtos.InformativeGetRequest;
import com.weg.infoweg.modules.informative.application.dtos.InformativeGetResponse;
import com.weg.infoweg.modules.informative.domain.Informative;
import com.weg.infoweg.modules.informative.domain.exceptions.InformativeNotFoundException;
import com.weg.infoweg.modules.informative.domain.ports.InformativeRepository;
import org.springframework.stereotype.Component;

@Component
public class GetInformativeCase {

    private final InformativeRepository informativeRepository;

    public GetInformativeCase(InformativeRepository informativeRepository) {
        this.informativeRepository = informativeRepository;
    }

    public InformativeGetResponse execute(InformativeGetRequest informativeGetRequest) {
        Informative informative = informativeRepository.findById(informativeGetRequest.id()).orElseThrow(() -> new InformativeNotFoundException("Informative not found"));
        return new InformativeGetResponse(informative.getId(), informative.getTitle(), informative.getDescription(), informative.getImage());
    } //UUID id, String title, String description, byte[] image
}
