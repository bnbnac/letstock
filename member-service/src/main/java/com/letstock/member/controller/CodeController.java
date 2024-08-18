package com.letstock.member.controller;

import com.letstock.member.dto.request.MailVerify;
import com.letstock.member.service.CodeService;
import com.letstock.member.service.MailService;
import com.letstock.member.dto.request.MailSend;
import com.letstock.member.exception.MailSendFail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CodeController {

    private final MailService mailService;
    private final CodeService codeService;

    @PostMapping("/code/mail")
    public void send(@RequestBody @Valid MailSend mailSend) {
        try {
            String code = mailService.sendCode(mailSend.getEmail());
            codeService.save(code, mailSend.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MailSendFail();
        }
    }

    @PostMapping("/code/mail/verification")
    public void mailVerify(@RequestBody @Valid MailVerify mailVerify) {
        codeService.verify(mailVerify.getCode(), mailVerify.getEmail());
    }
}
