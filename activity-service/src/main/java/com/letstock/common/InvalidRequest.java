package com.letstock.common;

public class InvalidRequest extends LetstockException {

    private static final String MESSAGE = "잘못된 요청입니다";

    public InvalidRequest(String field, String message) {
        super(MESSAGE);
        setValidation(field, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
