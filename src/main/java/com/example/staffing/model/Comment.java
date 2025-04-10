package com.example.staffing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String comment;

    @ManyToOne
    @JsonIgnore
    private StaffingProcess staffingProcess;

    @ManyToOne
    @JoinColumn(name = "author_id")
    public Employee author;

    private Long commentParent;


}
