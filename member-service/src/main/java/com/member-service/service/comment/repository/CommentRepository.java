package com.mod1.service.comment.repository;

import com.mod1.service.comment.domain.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
