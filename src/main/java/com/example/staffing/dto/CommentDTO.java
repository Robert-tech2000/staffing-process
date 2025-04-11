package com.example.staffing.dto;

import com.example.staffing.util.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO extends AbstractEntity {

    private String title;
    private String comment;
    private StaffingProcessDTO staffingProcess;
    public EmployeeDTO author;
    private Long commentParent;

}
