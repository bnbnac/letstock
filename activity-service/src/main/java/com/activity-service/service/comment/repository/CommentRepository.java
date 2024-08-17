package com.mod2.service.comment.repository;

import com.mod2.service.comment.domain.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
