package com.example.staffing.dto;

import com.example.staffing.model.Client;
import com.example.staffing.util.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StaffingProcessDTO extends AbstractEntity {

    private String title;
    private Client client;
    private EmployeeDTO employee;
    private List<CommentDTO> comments;
    private boolean isActive;

}
