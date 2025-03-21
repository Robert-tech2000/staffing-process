package com.example.staffing.repository;

import com.example.staffing.model.Comment;
import com.example.staffing.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
