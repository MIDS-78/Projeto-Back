package com.weg.infoweg.modules.informative.domain.ports;

import com.weg.infoweg.modules.informative.domain.Informative;

import java.util.Optional;
import java.util.UUID;

public interface InformativeRepository {

    /**
     * Busca um informativo pelo seu ID.
     * @param id O UUID do informativo.
     * @return um Optional contendo o Informativo se encontrado, ou um Optional vazio caso contrário.
     */
    Optional<Informative> findById(UUID id);

    // Você vai adicionar outros métodos aqui conforme a necessidade (save, delete, findAll, etc.)
    // Informative save(Informative informative);
    // void deleteById(UUID id);
}
