package com.mod2.service.post.respository;

import com.mod2.service.post.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
