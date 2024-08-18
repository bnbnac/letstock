package com.letstock.post.exception;

import com.letstock.common.LetstockException;

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
