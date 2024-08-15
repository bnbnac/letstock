package com.letstock.service.post.service;

import com.letstock.service.post.domain.Post;
import com.letstock.service.post.dto.request.PostCreate;
import com.letstock.service.post.exception.PostNotFound;
import com.letstock.service.post.respository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Long write(PostCreate postCreate, Long memberId) {
        return postRepository.save(createPost(postCreate, memberId)).getId();
    }

    private Post createPost(PostCreate postCreate, Long memberId) {
        return Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .memberId(memberId)
                .build();
    }

    public Long getOwnerId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);
        return post.getMemberId();
    }

}
