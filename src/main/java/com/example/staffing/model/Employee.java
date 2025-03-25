package com.example.staffing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private boolean isAvailable = true;

    @OneToMany
    @JsonIgnore
    private List<StaffingProcess> staffingProcesses;

    public Employee(String name, boolean isAvailable) {
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public Employee() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<StaffingProcess> getStaffingProcesses() {
        return staffingProcesses;
    }

    public void setStaffingProcesses(List<StaffingProcess> staffingProcesses) {
        this.staffingProcesses = staffingProcesses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
