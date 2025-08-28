package com.weg.infoweg.infrastructure.persistence.Informative;

import com.weg.infoweg.modules.informative.domain.Informative;
import com.weg.infoweg.modules.informative.domain.ports.InformativeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class InformativeRepositoryJpaAdapter implements InformativeRepository {

    private final InformativeRepositoryJpa informativeRepositoryJpa;

    public InformativeRepositoryJpaAdapter(InformativeRepositoryJpa informativeRepositoryJpa) {
        this.informativeRepositoryJpa = informativeRepositoryJpa;
    }

    @Override
    public Optional<Informative> findById(UUID id) {
        return informativeRepositoryJpa.findById(id);
    }

    @Override
    public Informative save(Informative informative) {
        return informativeRepositoryJpa.save(informative);
    }

    @Override
    public void deleteById(UUID id) {
        informativeRepositoryJpa.deleteById(id);
    }
}
