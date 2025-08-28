package com.weg.infoweg.modules.informative.domain.cases;

import com.weg.infoweg.modules.informative.aplication.dtos.InformativeDeleteRequest;
import com.weg.infoweg.modules.informative.domain.exception.InformativeNotFoundException;
import com.weg.infoweg.modules.informative.domain.exception.UserWithoutPermissionInformativeException;
import com.weg.infoweg.modules.informative.ports.InformativeRepository;
import com.weg.infoweg.modules.user.domain.User;
import com.weg.infoweg.modules.user.domain.exceptions.UserNotFoundException;
import com.weg.infoweg.modules.user.domain.ports.UserRepository;

import java.util.UUID;

public class DeleteInformativeCase {

    private final InformativeRepository informativeRepository;
    private final UserRepository userRepository;

    public DeleteInformativeCase(InformativeRepository informativeRepository, UserRepository userRepository) {
        this.informativeRepository = informativeRepository;
        this.userRepository = userRepository;
    }

    public void execute(InformativeDeleteRequest request, UUID idUserExecuter) {

        User user = userRepository.findById(idUserExecuter)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.canDeleteInformative()) {
            throw new UserWithoutPermissionInformativeException("User is not authorized to delete informatives");
        }

        var informative = informativeRepository.findById(request.id());

        if (informative.isPresent()) {
            informativeRepository.deleteById(request.id());
            return;
        }

        throw new InformativeNotFoundException("Record not found by ID: " + request.id());
    }
}
