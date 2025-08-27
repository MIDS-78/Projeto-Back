package com.weg.infoweg.modules.informative.domain.cases;

import com.weg.infoweg.modules.informative.aplication.dtos.InformativeDeleteRequest;
import com.weg.infoweg.modules.informative.domain.exception.InformativeNotFoundException;
import com.weg.infoweg.modules.informative.ports.InformativeRepository;

public class DeleteInformativeCase {

    private final InformativeRepository informativeRepository;

    public DeleteInformativeCase(InformativeRepository informativeRepository) {
        this.informativeRepository = informativeRepository;
    }

    public void execute(InformativeDeleteRequest request) {
        var informative = informativeRepository.findById(request.id());

        if (informative.isPresent()) {
            informativeRepository.deleteById(request.id());
            return;
        }
        throw new InformativeNotFoundException("Record not found by ID: " + request.id());
    }
}
