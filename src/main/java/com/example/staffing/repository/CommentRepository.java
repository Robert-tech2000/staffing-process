package com.example.staffing.repository;

import com.example.staffing.model.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByStaffingProcessId(Long staffingProcessId);
}
