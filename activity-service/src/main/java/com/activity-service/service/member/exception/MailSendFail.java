package com.mod2.service.member.exception;

import com.mod2.service.common.LetstockException;
import com.mod2.service.common.LetstockException;

public class MailSendFail extends LetstockException {

    private static final String MESSAGE = "메일 발송에 실패했습니다";

    public MailSendFail() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 500;
    }

}
