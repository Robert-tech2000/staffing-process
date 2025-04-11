package com.example.staffing.dto;

import com.example.staffing.util.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeDTO extends AbstractEntity {

    private String name;
    private boolean isAvailable = true;
    private List<StaffingProcessDTO> staffingProcesses;
}
