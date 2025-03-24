package com.example.staffing.dto;

import com.example.staffing.model.StaffingProcess;

import java.util.List;

public class ClientDTO {

    private Long id;
    private String clientName;
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
