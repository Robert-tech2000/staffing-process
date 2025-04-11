package com.example.staffing.model;

import com.example.staffing.util.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role  extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String name;

}
