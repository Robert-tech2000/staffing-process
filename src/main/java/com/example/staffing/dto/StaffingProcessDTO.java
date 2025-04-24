package com.example.staffing.dto;

import com.example.staffing.model.Client;
import com.example.staffing.model.Comment;
import com.example.staffing.model.User;
import com.example.staffing.util.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StaffingProcessDTO extends AbstractEntity {

    private String title;
    private Client client;
    private User employee;
    private List<Comment> comments;
    private boolean isActive;

}
