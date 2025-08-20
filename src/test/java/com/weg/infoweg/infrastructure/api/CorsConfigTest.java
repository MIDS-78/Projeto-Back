package com.weg.infoweg.infrastructure.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CorsConfigTest {

    @Autowired
    private UrlBasedCorsConfigurationSource corsConfigurationSource;

    @Test
    void corsConfigurationBeanShouldBeLoaded() {
        assertThat(corsConfigurationSource).isNotNull();

        // Pega todas as configurações registradas
        CorsConfiguration config = corsConfigurationSource.getCorsConfigurations().get("/**");

        assertThat(config).isNotNull();
        assertThat(config.getAllowedOrigins()).containsExactly("https://infoweg.vercel.app");
        assertThat(config.getAllowedMethods()).containsExactlyInAnyOrder("GET", "POST", "PUT", "DELETE", "OPTIONS");
        assertThat(config.getAllowedHeaders()).contains("*");
        assertThat(config.getAllowCredentials()).isTrue();
    }
}
