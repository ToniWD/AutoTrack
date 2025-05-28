package com.AutoTrack.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User extends Person {

    @JsonIgnore
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @NotNull(message = "Password_hash cannot be null")
    @NotBlank(message = "Password_hash cannot be blank")
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @NotNull(message = "Role cannot be null")
    @NotBlank(message = "Role cannot be blank")
    @Column(name = "role", nullable = false)
    private String role;

    public User() {
    }

    public User(String firstName, String lastName, String email, String phone, String address, String username, String passwordHash, String role) {
        super(firstName, lastName, email, phone, address);
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
