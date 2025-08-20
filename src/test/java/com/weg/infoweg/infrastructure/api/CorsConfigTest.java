package com.weg.infoweg.infrastructure.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class CorsConfigTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Testa o comportamento do CORS para uma origem permitida.
     * Deve passar a pré-validação (preflight) e retornar um status OK.
     */
    @Test
    void shouldAllowPreflightRequestForAllowedOrigin() throws Exception {
        // Envia uma requisição OPTIONS (preflight) para um endpoint público.
        // A requisição inclui o cabeçalho 'Origin' para simular a origem do front-end.
        mockMvc.perform(options("/api/auth/login")
                        .header("Origin", "https://infoweg.vercel.app")
                        .header("Access-Control-Request-Method", "POST"))
                // Espera um status HTTP 200 (OK)
                .andExpect(status().isOk())
                // Espera que o cabeçalho 'Access-Control-Allow-Origin' tenha o valor da origem.
                .andExpect(header().string("Access-Control-Allow-Origin", "https://infoweg.vercel.app"))
                // **CORREÇÃO:** Altera a asserção para verificar se a string retornada 'contém' 'POST'.
                .andExpect(header().string("Access-Control-Allow-Methods", containsString("POST")));
    }

    /**
     * Testa o comportamento do CORS para uma origem não permitida.
     * A requisição deve ser negada, retornando um status Forbidden ou similar,
     * e os cabeçalhos de CORS não devem ser definidos para a origem maliciosa.
     */
    @Test
    void shouldDenyPreflightRequestForForbiddenOrigin() throws Exception {
        // Envia uma requisição OPTIONS com uma origem que não está na sua lista de permitidos.
        mockMvc.perform(options("/api/auth/login")
                        .header("Origin", "https://malicious-site.com")
                        .header("Access-Control-Request-Method", "POST"))
                // Espera um status HTTP 403 (Forbidden)
                .andExpect(status().isForbidden());
    }
}
