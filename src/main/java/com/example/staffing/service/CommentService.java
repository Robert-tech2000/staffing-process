package com.example.staffing.service;

import com.example.staffing.dto.CommentDTO;
import com.example.staffing.model.Comment;
import com.example.staffing.model.StaffingProcess;
import com.example.staffing.repository.CommentRepository;
import com.example.staffing.repository.StaffingProcessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository repository;
    private final StaffingProcessRepository staffingProcessRepository;
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    public CommentService(CommentRepository repository, StaffingProcessRepository staffingProcessRepository) {
        this.repository = repository;
        this.staffingProcessRepository = staffingProcessRepository;
    }


    public CommentDTO addComment(String title, String commentMessages, Long staffingProcessId, Long parentCommentId) {
        Comment comment = new Comment();
        StaffingProcess staffingProcess = staffingProcessRepository.findById(staffingProcessId).orElseThrow();
        comment.setTitle(title);
        comment.setComment(commentMessages);
        comment.setStaffingProcess(staffingProcess);
        comment.setCommentParent(parentCommentId);

        repository.save(comment);
        updateStaffingProcessWithNewComment(staffingProcess, comment);
        logger.info("Comment created successfully with ID: {}", comment.getId());
        return convertCommentToDTO(comment);
    }

    private void updateStaffingProcessWithNewComment(StaffingProcess staffingProcess, Comment comment) {
        List<Comment> existingComments = staffingProcess.getComments();
        existingComments.add(comment);
        staffingProcess.setComments(existingComments);
    }


    public CommentDTO getComment(Long commentId) {
        Comment comment = repository.findById(commentId).orElseThrow();
        logger.info("Comment found with ID: {}", comment.getId());
        return convertCommentToDTO(comment);
    }

    public List<CommentDTO> getAllComments() {
        logger.info("Get all comments");
        return Streamable.of(repository.findAll()).toList().stream().map(this::convertCommentToDTO).toList();
    }

    public CommentDTO updateComment(CommentDTO comment) {
        return null;
    }

    public void deleteComment(Long commentId) {
        logger.info("Delete comment with ID: {}", commentId);
        repository.deleteById(commentId);
    }

    private CommentDTO convertCommentToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setTitle(comment.getTitle());
        commentDTO.setComment(comment.getComment());
        commentDTO.setStaffingProcess(comment.getStaffingProcess());
        commentDTO.setCommentParent(comment.getCommentParent());
        return commentDTO;
    }

}
