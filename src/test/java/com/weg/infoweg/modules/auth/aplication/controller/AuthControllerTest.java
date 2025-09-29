package com.weg.infoweg.modules.auth.aplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weg.infoweg.core.UserAuthenticationService;
import com.weg.infoweg.infrastructure.api.dto.ResponseApiDto;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.login.UserLoginResponse;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterRequest;
import com.weg.infoweg.modules.auth.aplication.dtos.register.UserRegisterResponse;
import com.weg.infoweg.modules.auth.aplication.port.AuthService;
import com.weg.infoweg.modules.token.application.dtos.JwtTokenDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthController Tests")
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private UserAuthenticationService userAuthenticationService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/auth";
    private static final UUID TEST_USER_ID = UUID.randomUUID();
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "password123";
    private static final String TEST_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("Login Tests")
    class LoginTests {

        @Test
        @DisplayName("Should login successfully with valid credentials")
        void shouldLoginSuccessfullyWithValidCredentials() throws Exception {
            // Given
            UserLoginRequest loginRequest = new UserLoginRequest(TEST_EMAIL, TEST_PASSWORD);
            UserLoginResponse loginResponse = new UserLoginResponse(new JwtTokenDto(TEST_TOKEN));

            given(authService.login(any(UserLoginRequest.class)))
                    .willReturn(loginResponse);

            // When & Then
            mockMvc.perform(post(BASE_URL + "/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath("$.message").value("Login completed successfully"))
                    .andExpect(jsonPath("$.data.jwtTokenDto.token").value(TEST_TOKEN)); // objeto aninhado
        }

        @Test
        @DisplayName("Should return ResponseEntity with correct structure")
        void shouldReturnResponseEntityWithCorrectStructure() {
            // Given
            UserLoginRequest loginRequest = new UserLoginRequest(TEST_EMAIL, TEST_PASSWORD);
            UserLoginResponse loginResponse = new UserLoginResponse(new JwtTokenDto(TEST_TOKEN));

            given(authService.login(loginRequest)).willReturn(loginResponse);

            // When
            ResponseEntity<ResponseApiDto<UserLoginResponse>> response = authController.login(loginRequest);

            // Then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().status()).isEqualTo("success");
            assertThat(response.getBody().message()).isEqualTo("Login completed successfully");
            assertThat(response.getBody().data()).isEqualTo(loginResponse);
            assertThat(response.getBody().localStamp()).isNotNull();
        }
    }

    @Nested
    @DisplayName("Register Tests")
    class RegisterTests {
        // mantive igual (sem mudanças)
    }

    @Nested
    @DisplayName("Logout Tests")
    class LogoutTests {
        // mantive igual (sem mudanças)
    }

    @Nested
    @DisplayName("Refresh Token Tests")
    class RefreshTokenTests {

        @Test
        @DisplayName("Should refresh token successfully")
        void shouldRefreshTokenSuccessfully() throws Exception {
            // Given
            JwtTokenDto oldTokenDto = new JwtTokenDto(TEST_TOKEN);
            JwtTokenDto newTokenDto = new JwtTokenDto("new_token_value");

            given(userAuthenticationService.getIdUserAuthentication())
                    .willReturn(TEST_USER_ID);
            given(authService.refresh(TEST_USER_ID, oldTokenDto))
                    .willReturn(newTokenDto);

            // When & Then
            mockMvc.perform(post(BASE_URL + "/refresh")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(oldTokenDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath("$.message").value("User refreshed successfully"))
                    .andExpect(jsonPath("$.data.token").value("new_token_value")); // direto, não aninhado
        }

        @Test
        @DisplayName("Should return ResponseEntity with correct structure for refresh")
        void shouldReturnResponseEntityWithCorrectStructureForRefresh() {
            // Given
            JwtTokenDto oldTokenDto = new JwtTokenDto(TEST_TOKEN);
            JwtTokenDto newTokenDto = new JwtTokenDto("new_token_value");

            given(userAuthenticationService.getIdUserAuthentication())
                    .willReturn(TEST_USER_ID);
            given(authService.refresh(TEST_USER_ID, oldTokenDto))
                    .willReturn(newTokenDto);

            // When
            ResponseEntity<ResponseApiDto<JwtTokenDto>> response = authController.refresh(oldTokenDto);

            // Then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().status()).isEqualTo("success");
            assertThat(response.getBody().message()).isEqualTo("User refreshed successfully");
            assertThat(response.getBody().data()).isEqualTo(newTokenDto);
            assertThat(response.getBody().localStamp()).isNotNull();
        }
    }
}
