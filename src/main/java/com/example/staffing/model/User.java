package com.example.staffing.model;

import com.example.staffing.util.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class User  extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @OneToMany
    private Set<Role> roles;

    @OneToOne
    private Employee employee;
}
