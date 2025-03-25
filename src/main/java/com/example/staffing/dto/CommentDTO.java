package com.example.staffing.dto;

import com.example.staffing.model.StaffingProcess;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class CommentDTO {

    private Long id;

    private StaffingProcess staffingProcess;

    private String comment;

    private Long commentParent;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public StaffingProcess getStaffingProcess() {
        return staffingProcess;
    }

    public void setStaffingProcess(StaffingProcess staffingProcess) {
        this.staffingProcess = staffingProcess;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCommentParent() {
        return commentParent;
    }

    public void setCommentParent(Long commentParent) {
        this.commentParent = commentParent;
    }
}
