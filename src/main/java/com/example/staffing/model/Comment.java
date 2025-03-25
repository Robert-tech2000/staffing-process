package com.example.staffing.model;

import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private StaffingProcess staffingProcess;

    private String title;

    private String comment;

    private Long commentParent;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // todo: add commenter ? Is person who added comment employee or admin, or other role ?

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
}
