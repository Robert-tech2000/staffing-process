package com.example.staffing.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class User {
    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @OneToMany
    private Set<Role> roles;

    @OneToOne
    private Employee employee;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
