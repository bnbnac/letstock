package com.mod3.service.comment.service;

import com.mod3.service.comment.domain.Comment;
import com.mod3.service.comment.dto.request.CommentCreate;
import com.mod3.service.comment.exception.CommentNotFound;
import com.mod3.service.comment.repository.CommentRepository;
import com.mod3.service.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public Long write(Long postId, CommentCreate commentCreate, Long memberId) {
        Comment comment = createComment(postId, commentCreate, memberId);
        return commentRepository.save(comment).getId();
    }

    private Comment createComment(Long postId, CommentCreate commentCreate, Long memberId) {
        return Comment.builder()
                .postId(postId)
                .content(commentCreate.getContent())
                .memberId(memberId)
                .build();
    }

    public Long getOwnerId(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFound::new);
        return comment.getMemberId();
    }
}
