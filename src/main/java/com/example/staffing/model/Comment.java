package com.example.staffing.model;

import com.example.staffing.util.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Comment  extends AbstractEntity {

    private String title;
    private String comment;

    @ManyToOne
    @JsonIgnore
    private StaffingProcess staffingProcess;

    @ManyToOne
    @JoinColumn(name = "author_id")
    public User author;

    private Long commentParent;


}
