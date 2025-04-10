package com.example.staffing.dto;

import com.example.staffing.model.StaffingProcess;

import java.util.List;

import com.example.staffing.util.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO extends AbstractEntity {

    private String clientName;
    private List<StaffingProcess> staffingProcesses;
}
