package com.example.staffing.dto;

import com.example.staffing.model.Client;
import com.example.staffing.model.Comment;
import com.example.staffing.model.Employee;

import java.util.List;

public class StaffingProcessDTO {

    private Long id;

    private String title;

    private Client client;

    private Employee employee;

    private List<Comment> comments;

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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
