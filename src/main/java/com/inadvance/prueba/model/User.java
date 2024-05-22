package com.inadvance.prueba.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @CreationTimestamp
    private LocalDate created;
    @UpdateTimestamp
    private LocalDate modified;
    private LocalDate lastLogin;
    private String token;
    private boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PhoneEntity> phones;
}