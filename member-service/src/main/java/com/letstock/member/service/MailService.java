package com.letstock.member.service;

import com.letstock.member.common.InvalidRequest;
import com.letstock.member.config.property.AuthProperty;
import com.letstock.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;

    public String sendCode(String toMail) throws MessagingException {
        validateSignedUpMail(toMail);
        String code = createCode();
        MimeMessage message = CreateMessage(code, toMail);
        javaMailSender.send(message);
        return code;
    }

    private void validateSignedUpMail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new InvalidRequest("email", "이미 가입된 이메일입니다");
                });
    }

    private String createCode() {
        return String.valueOf((int) (Math.random() * 90_000_000) + 10_000_000);
    }

    private MimeMessage CreateMessage(String code, String toMail) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(AuthProperty.MAIL_SENDER);
        message.setRecipients(MimeMessage.RecipientType.TO, toMail);
        message.setSubject("이메일 인증");
        message.setText(generateEmailBody(code), "UTF-8", "html");

        return message;
    }

    private String generateEmailBody(String code) {
        return "<h3>" + "letstock에서 요청하신 인증 번호 발송" + "</h3>" +
                "<h1>" + code + "</h1>" +
                "<h3>" + "이용해주셔서 감사합니다." + "</h3>";
    }


}
