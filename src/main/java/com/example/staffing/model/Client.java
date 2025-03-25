package com.example.staffing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clientName;

    @OneToMany
    @JsonIgnore
    private List<StaffingProcess> staffingProcesses;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<StaffingProcess> getStaffingProcesses() {
        return staffingProcesses;
    }

    public void setStaffingProcesses(List<StaffingProcess> staffingProcesses) {
        this.staffingProcesses = staffingProcesses;
    }
}
