package com.weg.infoweg.modules.user.domain;

import com.weg.infoweg.infrastructure.persistence.user.converter.EmailConverter;
import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import com.weg.infoweg.modules.user.domain.exceptions.*;
import jakarta.persistence.*;
import com.weg.infoweg.modules.user.domain.valueobjects.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 60)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    @Convert(converter = EmailConverter.class)
    private Email email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "phone_number", nullable = false, length = 40)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_level", nullable = false)
    private AccessLevel accessLevel;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();

    public User(UUID id, String username, Email email, String passwordHash, String phoneNumber, AccessLevel accessLevel, UUID createdBy, LocalDateTime createdAt, UUID updatedBy, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.accessLevel = accessLevel;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public User() {

    }

    public User(@NotNull(message = "Username is required") String username, @NotNull(message = "Email is required") Email email, @NotNull(message = "Password is required") @Size(min=8, message = "Password must be at least 8 characters") String password, @NotNull(message = "Phone number is required") String phoneNumber, AccessLevel accessLevel) {
        this.id = null;
        this.username = username;
        this.email = email;
        this.passwordHash = password;
        this.phoneNumber = phoneNumber;
        this.accessLevel = accessLevel;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Email getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // SETTERS COM VALIDAÇÕES

    public void setId(UUID id) {
        if (id == null) {
            throw new InvalidIdException("ID cannot be null.");
        }
        this.id = id;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidUsernameException("Username cannot be null or empty");
        }
        if (username.length() > 60) {
            throw new InvalidUsernameException("Username is too long");
        }
        this.username = username.trim();
    }

    public void setEmail(Email email) {
        if (email == null) {
            throw new InvalidEmailException("Email cannot be null or empty.");
        }
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.isEmpty()) {
            throw new InvalidPasswordException("Password hash cannot be null or empty.");
        }
        this.passwordHash = passwordHash;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new InvalidPhoneNumberException("Phone number cannot be null or empty.");
        }

        String onlyDigits = phoneNumber.replaceAll("[^\\d]", "");
        if (onlyDigits.length() < 10 || onlyDigits.length() > 15) {
            throw new InvalidPhoneNumberException("Must contain between 10 and 15 digits.");
        }

        this.phoneNumber = onlyDigits;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        if (accessLevel == null) {
            throw new InvalidAccessLevelException("Access level cannot be null.");
        }
        this.accessLevel = accessLevel;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        if (createdAt == null) {
            throw new InvalidDateException("CreatedAt cannot be null.");
        }
        this.createdAt = createdAt;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        if (updatedAt == null) {
            throw new InvalidDateException("UpdatedAt cannot be null.");
        }
        this.updatedAt = updatedAt;
    }

    // equals & hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}