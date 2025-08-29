package com.weg.infoweg.modules.informative.ports;



import com.weg.infoweg.modules.informative.domain.Informative;

import java.util.Optional;
import java.util.UUID;

public interface InformativeRepository {

    Optional<Informative> findById(UUID id);

    Informative save(Informative informative);

    boolean deleteById(UUID id);

}