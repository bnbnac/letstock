package com.letstock.service.member.exception;

import com.letstock.service.common.LetstockException;

public class MemberNotFound extends LetstockException {

    private static final String MESSAGE = "존재하지 않는 멤버입니다";

    public MemberNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

}