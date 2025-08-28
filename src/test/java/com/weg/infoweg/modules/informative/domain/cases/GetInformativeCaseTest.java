package com.weg.infoweg.modules.informative.domain.cases;

import com.weg.infoweg.modules.informative.application.dtos.InformativeGetRequest;
import com.weg.infoweg.modules.informative.domain.Informative;
import com.weg.infoweg.modules.informative.domain.ports.InformativeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetInformativeCaseTest {
    private InformativeRepository informativeRepository;
    private GetInformativeCase getInformativeCase;

    @BeforeEach
    void setup(){
        informativeRepository = mock(InformativeRepository.class);
        getInformativeCase = new GetInformativeCase(informativeRepository);
    }

    @Test
    void shouldReturnUserSuccessfully(){
        UUID id = UUID.randomUUID();
        InformativeGetRequest request = new InformativeGetRequest(id);

        Informative informative = new Informative(UUID.randomUUID(), "TESTE", "MIDS 78", "dados_de_imagem_de_teste".getBytes());
    }
}
