package com.letstock.service.member.exception;

import com.letstock.service.common.LetstockException;

public class TokenNotFound extends LetstockException {

    private static final String MESSAGE = "토큰이 존재하지 않습니다";

    public TokenNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }

}
