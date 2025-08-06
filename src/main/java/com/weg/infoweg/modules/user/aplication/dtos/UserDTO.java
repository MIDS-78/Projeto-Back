package com.weg.infoweg.modules.user.aplication.dtos;

import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import jakarta.validation.constraints.*;

public class UserDTO {

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(max = 60, message = "O nome de usuário deve ter no máximo 60 caracteres")
    private String username;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O e-mail deve ser válido")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String password;

    @NotBlank(message = "O número de telefone é obrigatório")
    @Pattern(regexp = "^[+]?\\d{10,15}$", message = "Número de telefone inválido")
    private String phoneNumber;

    @NotNull(message = "O nível de acesso é obrigatório")
    private AccessLevel accessLevel;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }}
