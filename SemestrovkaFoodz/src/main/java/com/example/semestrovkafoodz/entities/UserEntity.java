package com.example.semestrovkafoodz.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private String email;

    @OneToMany(mappedBy = "productId")
    private List<ProductEntity> productEntities;

    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

    @Enumerated(value = EnumType.STRING)
    private State state = State.ACTIVE;

    public enum Role {
        USER, ADMIN
    }

    public enum State {
        ACTIVE, BANNED
    }

    public String getRole() {
        if (this.role == Role.ADMIN) {
            return "ROLE_ADMIN";
        } else {
            return "ROLE_USER";
        }
    }

    public boolean isActive() { return this.state == State.ACTIVE; }
    public boolean isBanned() { return this.state == State.BANNED; }
    public boolean isAdmin() { return this.role == Role.ADMIN; }
}
