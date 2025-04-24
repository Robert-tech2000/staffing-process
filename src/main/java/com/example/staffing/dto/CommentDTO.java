package com.example.staffing.dto;

import com.example.staffing.model.StaffingProcess;
import com.example.staffing.model.User;
import com.example.staffing.util.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO extends AbstractEntity {

    private String title;
    private String comment;
    private StaffingProcess staffingProcess;
    public User author;
    private Long commentParent;

}
