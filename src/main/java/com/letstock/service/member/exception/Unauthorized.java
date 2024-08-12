package com.letstock.service.member.exception;

import com.letstock.service.common.LetstockException;

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
