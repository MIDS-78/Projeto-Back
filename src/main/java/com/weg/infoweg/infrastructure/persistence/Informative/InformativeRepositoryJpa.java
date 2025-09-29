package com.weg.infoweg.infrastructure.persistence.Informative;

import com.weg.infoweg.modules.informative.domain.Informative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InformativeRepositoryJpa extends JpaRepository<Informative, UUID> {
}
