package com.example.staffing.model;

import com.example.staffing.util.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Client extends AbstractEntity {

    @OneToMany
    @JsonIgnore
    private List<StaffingProcess> staffingProcesses;

    private String clientName;

}
