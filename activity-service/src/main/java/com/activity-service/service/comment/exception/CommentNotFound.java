package com.mod2.service.comment.exception;

import com.mod2.service.common.LetstockException;
import com.mod2.service.common.LetstockException;

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
