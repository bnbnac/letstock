package com.mod3.service.post.service;

import com.mod3.service.post.domain.Post;
import com.mod3.service.post.dto.request.PostCreate;
import com.mod3.service.post.exception.PostNotFound;
import com.mod3.service.post.respository.PostRepository;
import com.mod3.service.post.respository.PostRepository;
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
