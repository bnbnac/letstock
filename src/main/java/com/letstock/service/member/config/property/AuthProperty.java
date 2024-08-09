package com.letstock.service.member.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "auth")
public class AuthProperty {

    public static final String MAIL_SENDER = "tftad.com@gmail.com";

    private Long authCodeDurationMinutes;
    private Long emailAuthCodeDurationMinutes;

}
