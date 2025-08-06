package com.weg.infoweg.modules.user.domain;

import com.weg.infoweg.modules.user.domain.enums.AccessLevel;
import com.weg.infoweg.modules.user.exceptions.*;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 60)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private byte[] passwordHash;

    @Column(name = "phone_number", nullable = false, length = 40)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_level", nullable = false)
    private AccessLevel accessLevel;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public User(UUID id, String username, String email, byte[] passwordHash, String phoneNumber,
                AccessLevel accessLevel, Integer createdBy, LocalDateTime createdAt,
                Integer updatedBy, LocalDateTime updatedAt) {
        this.setId(id);
        this.setUsername(username);
        this.setEmail(email);
        this.setPasswordHash(passwordHash);
        this.setPhoneNumber(phoneNumber);
        this.setAccessLevel(accessLevel);
        this.setCreatedBy(createdBy);
        this.setCreatedAt(createdAt);
        this.setUpdatedBy(updatedBy);
        this.setUpdatedAt(updatedAt);
    }

    // GETTERS

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
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

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidEmailException("Email cannot be null or empty.");
        }

        String trimmed = email.trim().toLowerCase();
        if (!trimmed.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new InvalidEmailException("Invalid format: " + trimmed);
        }

        this.email = trimmed;
    }

    public void setPasswordHash(byte[] passwordHash) {
        if (passwordHash == null || passwordHash.length == 0) {
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

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        if (createdAt == null) {
            throw new InvalidDateException("CreatedAt cannot be null.");
        }
        this.createdAt = createdAt;
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
