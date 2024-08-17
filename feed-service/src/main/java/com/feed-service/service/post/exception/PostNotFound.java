package com.mod3.service.post.exception;

import com.mod3.service.common.LetstockException;
import com.mod3.service.common.LetstockException;

public class PostNotFound extends LetstockException {

    private static final String MESSAGE = "포스트가 존재하지 않습니다";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

}
