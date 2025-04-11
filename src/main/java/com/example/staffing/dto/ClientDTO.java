package com.example.staffing.dto;

import com.example.staffing.util.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientDTO extends AbstractEntity {

    private String clientName;
    private List<StaffingProcessDTO> staffingProcesses;
}
