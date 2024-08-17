package com.mod1.service.member.controller;

import com.mod1.service.member.dto.request.MailSend;
import com.mod1.service.member.dto.request.MailVerify;
import com.mod1.service.member.exception.MailSendFail;
import com.mod1.service.member.service.CodeService;
import com.mod1.service.member.service.MailService;
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
