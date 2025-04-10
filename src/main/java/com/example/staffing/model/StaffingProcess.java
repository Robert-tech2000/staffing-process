package com.example.staffing.model;

import com.example.staffing.util.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class StaffingProcess extends AbstractEntity {

    private String title;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    private Employee employee;

    @OneToMany
    private List<Comment> comments;

}
