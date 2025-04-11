package com.example.staffing.dto;

import com.example.staffing.model.Client;
import com.example.staffing.model.Comment;
import com.example.staffing.model.Employee;

import java.util.List;

import com.example.staffing.util.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffingProcessDTO extends AbstractEntity {

    private String title;
    private Client client;
    private Employee employee;
    private List<Comment> comments;
    private boolean isActive;

}
