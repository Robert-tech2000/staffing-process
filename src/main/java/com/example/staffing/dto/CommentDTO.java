package com.example.staffing.dto;

import com.example.staffing.model.Employee;
import com.example.staffing.model.StaffingProcess;

public class CommentDTO {

    private Long id;

    private String title;

    private String comment;

    private StaffingProcess staffingProcess;

    public Employee author;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Employee getAuthor() {
        return author;
    }

    public void setAuthor(Employee author) {
        this.author = author;
    }
}
