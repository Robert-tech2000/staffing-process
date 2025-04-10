package com.example.staffing.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @OneToMany
    private Set<Role> roles;

    @OneToOne
    private Employee employee;
}
