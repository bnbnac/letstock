package com.letstock.feed.common;

import lombok.Getter;

@Getter
public abstract class LetstockException extends RuntimeException {

    private ErrorValidation validation;

    public LetstockException(String message) {
        super(message);
    }

    public abstract int getStatusCode();

    public void setValidation(String field, String message) {
        this.validation = ErrorValidation.builder()
                .field(field)
                .message(message)
                .build();
    }

}
