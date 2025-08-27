package com.weg.infoweg.modules.informative.domain.cases;

import com.weg.infoweg.modules.informative.Informative;
import com.weg.infoweg.modules.informative.aplication.dtos.InformativeDeleteRequest;
import com.weg.infoweg.modules.informative.domain.exception.InformativeNotFoundException;
import com.weg.infoweg.modules.informative.ports.InformativeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteInformativeCaseTest {

    private InformativeRepository informativeRepository;
    private DeleteInformativeCase deleteInformativeCase;

    @BeforeEach
    void setUp() {
        informativeRepository = Mockito.mock(InformativeRepository.class);
        deleteInformativeCase = new DeleteInformativeCase(informativeRepository);
    }

    @Test
    void execute_shouldDelete_whenInformativeExists() {
        UUID id = UUID.fromString("f738ef97-67fb-4887-ace8-9c1afa1e8dda");

        // Simula que o findById encontrou um Informative real
        Informative informative = new Informative(); // precisa ter construtor público
        when(informativeRepository.findById(id)).thenReturn(Optional.of(informative));

        InformativeDeleteRequest request = new InformativeDeleteRequest(id);

        // Executa e verifica que não lança exceção
        assertDoesNotThrow(() -> deleteInformativeCase.execute(request));

        // Verifica se o deleteById foi chamado
        verify(informativeRepository).deleteById(id);
    }

    @Test
    void execute_shouldThrowException_whenInformativeDoesNotExist() {
        UUID id = UUID.fromString("f738ef97-67fb-4887-ace8-9c1afa1e8dda");

        when(informativeRepository.findById(id)).thenReturn(Optional.empty());

        InformativeDeleteRequest request = new InformativeDeleteRequest(id);

        // Verifica se lança a InformativeNotFoundException
        InformativeNotFoundException exception = assertThrows(
                InformativeNotFoundException.class,
                () -> deleteInformativeCase.execute(request)
        );

        assertEquals("Record not found by ID: " + id, exception.getMessage());

        // Verifica que deleteById nunca foi chamado
        verify(informativeRepository, never()).deleteById(id);
    }
}
