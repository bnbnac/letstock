package com.letstock.service.member.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "auth")
public class AuthProperty {

    public static final String EXPIRED_TOKEN = "expired";
    public static final String ID = "id";
    public static final String MAIL_SENDER = "tftad.com@gmail.com";

    private Long authCodeDurationMinutes;
    private Long emailAuthCodeDurationMinutes;
    private boolean cookieSecure;
    private String sameSite;
    private String accessTokenCookieName;
    private Long accessTokenCookieMaxAgeInDays;
    private String refreshTokenCookieName;
    private Long refreshTokenCookieMaxAgeInDays;

}
