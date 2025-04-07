package com.example.staffing.controller;

import com.example.staffing.dto.CommentDTO;
import com.example.staffing.service.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<CommentDTO> addComment(
            @RequestParam String title,
            @RequestParam String comment,
            @RequestParam("staff_process_id") Long staffingProcessId,
            @RequestParam(value = "comment_id", required = false) Long parentCommentId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addComment(title, comment, staffingProcessId, parentCommentId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("commentId") Long commentId) {
        CommentDTO comment = service.getComment(commentId);

        return comment.getId() != null
                ? ResponseEntity.ok(comment)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping()
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comments = service.getAllComments();
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comments);
    }

    @Transactional
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO comment) {
        return ResponseEntity.ok(service.updateComment(comment));
    }

    @Transactional
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("clientId") Long commentId) {
        service.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }


}
