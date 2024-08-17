package com.mod3.service.member.exception;

import com.mod3.service.common.LetstockException;

public class Unauthorized extends LetstockException {

    private static final String MESSAGE = "인증이 필요합니다";

    public Unauthorized() {
        super(MESSAGE);
    }

    public Unauthorized(String msg) {
        super(msg);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
