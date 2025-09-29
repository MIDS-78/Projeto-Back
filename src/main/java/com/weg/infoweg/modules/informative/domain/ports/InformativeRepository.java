package com.weg.infoweg.modules.informative.domain.ports;

import com.weg.infoweg.modules.informative.domain.Informative;

import java.util.Optional;
import java.util.UUID;

public interface InformativeRepository  {

    Optional<Informative> findById(UUID id);

    Informative save(Informative informative);

    void deleteById(UUID id);
}
