package com.letstock.comment.exception;

import com.letstock.common.LetstockException;

public class CommentNotFound extends LetstockException {

    private static final String MESSAGE = "댓글이 존재하지 않습니다";

    public CommentNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

}
