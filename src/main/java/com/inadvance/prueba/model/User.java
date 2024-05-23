package com.inadvance.prueba.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @NotEmpty(message = "Name is required")
    private String name;

    @Column(unique = true)
    @NotEmpty(message = "Email is required")
    private String email;

    private String password;

    @CreationTimestamp
    private LocalDate created;

    @UpdateTimestamp
    private LocalDate modified;

    private LocalDate lastLogin;

    @Column(length = 512)
    private String token;

    private boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Phone> phones;
}