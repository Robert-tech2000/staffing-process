package com.example.staffing.dto;

import com.example.staffing.model.Client;
import com.example.staffing.model.Employee;
import jakarta.persistence.*;

public class StaffingProcessDTO {

    private Long id;

    private Client client;

    private Employee employee;

    private boolean isActive;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
