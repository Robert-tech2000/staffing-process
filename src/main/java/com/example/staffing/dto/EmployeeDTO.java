package com.example.staffing.dto;

import com.example.staffing.model.StaffingProcess;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

public class EmployeeDTO {

    private Long id;
    private String name;
    private boolean isAvailable = true;
    private List<StaffingProcess> staffingProcesses;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StaffingProcess> getStaffingProcesses() {
        return staffingProcesses;
    }

    public void setStaffingProcesses(List<StaffingProcess> staffingProcesses) {
        this.staffingProcesses = staffingProcesses;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
